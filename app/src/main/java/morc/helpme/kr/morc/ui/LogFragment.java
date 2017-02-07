package morc.helpme.kr.morc.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import morc.helpme.kr.morc.R;
import morc.helpme.kr.morc.model.LogInfo;

public class LogFragment extends Fragment {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  private LogAdapter logAdapter;
  private LinearLayoutManager layoutManager;
  private boolean loading = false, noMore = false;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_log, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    setupUI();
  }

  private void setupUI() {
    recyclerView.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if(recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE && !loading) { //check for scroll down
          int visibleItemCount = layoutManager.getChildCount();
          int totalItemCount = layoutManager.getItemCount();

          if (visibleItemCount > 0
              && layoutManager.findLastVisibleItemPosition() >= totalItemCount - 1
              && totalItemCount > visibleItemCount
              && !noMore) {
            loading = true;
            noMore = !logAdapter.nextpage();
            Toast.makeText(getContext(), noMore ? R.string.nomore : R.string.loading_complete, Toast.LENGTH_SHORT).show();
            loading = false;
          }
        }
      }
    });

    Realm realm = Realm.getDefaultInstance();
    RealmResults<LogInfo> logInfoRealmResults = realm.where(LogInfo.class).findAllSorted("date", Sort.DESCENDING);
    logAdapter = new LogAdapter(recyclerView, logInfoRealmResults);
    logInfoRealmResults.addChangeListener(new RealmChangeListener<RealmResults<LogInfo>>() {
      @Override public void onChange(RealmResults<LogInfo> element) {
        logAdapter.notifyDataSetChanged();
      }
    });
    recyclerView.setAdapter(logAdapter);
  }

  @OnClick(R.id.fab) void onClickFAB() {
    new AlertDialog.Builder(getContext())
        .setTitle("삭제")
        .setMessage("정말 전부 삭제하시겠습니까?")
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.delete(LogInfo.class);
            realm.commitTransaction();
          }
        })
        .setNegativeButton(android.R.string.cancel, null)
        .show();
  }
}
