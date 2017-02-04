package morc.helpme.kr.morc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import morc.helpme.kr.morc.R;
import morc.helpme.kr.morc.model.LogInfo;

public class LogFragment extends Fragment {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  private LogAdapter logAdapter;

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
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    Realm realm = Realm.getDefaultInstance();
    RealmResults<LogInfo> logInfoRealmResults = realm.where(LogInfo.class).findAll();
    logAdapter = new LogAdapter(recyclerView, logInfoRealmResults);
    logInfoRealmResults.addChangeListener(new RealmChangeListener<RealmResults<LogInfo>>() {
      @Override public void onChange(RealmResults<LogInfo> element) {
        logAdapter.notifyDataSetChanged();
      }
    });
    recyclerView.setAdapter(logAdapter);
  }
}
