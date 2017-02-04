package morc.helpme.kr.morc.ui;

import android.support.transition.TransitionManager;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import morc.helpme.kr.morc.R;
import morc.helpme.kr.morc.model.LogInfo;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

  private RecyclerView recyclerView;
  private RealmResults<LogInfo> logInfoRealmResults;
  private int expandedPosition = -1;

  public LogAdapter(RecyclerView recyclerView, RealmResults<LogInfo> logInfoRealmResults) {
    this.recyclerView = recyclerView;
    this.logInfoRealmResults = logInfoRealmResults;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  @Override public void onBindViewHolder(ViewHolder holder, final int position) {
    LogInfo logInfo = logInfoRealmResults.get(position);
    holder.titleTextView.setText(logInfo.title);
    holder.dateTextView.setText(logInfo.date + " - ");
    holder.typeTextView.setText(logInfo.type);
    holder.typeTextView.setTextColor(logInfo.getColor());
    holder.expandButton.setVisibility(logInfo.exception != null ? View.VISIBLE : View.GONE);
    holder.exceptionTextView.setText(logInfo.exception);

    final boolean isExpaned = position == expandedPosition;
    holder.exceptionTextView.setVisibility(isExpaned? View.VISIBLE : View.GONE);
    holder.itemView.setActivated(isExpaned);
    holder.expandButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        expandedPosition = isExpaned ? -1 : position;
        TransitionManager.beginDelayedTransition(recyclerView);
        notifyDataSetChanged();
      }
    });

  }

  @Override public int getItemCount() {
    return logInfoRealmResults.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_title) TextView titleTextView;
    @BindView(R.id.text_date) TextView dateTextView;
    @BindView(R.id.text_type) TextView typeTextView;
    @BindView(R.id.button_expand) AppCompatImageButton expandButton;
    @BindView(R.id.text_exception) TextView exceptionTextView;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

}
