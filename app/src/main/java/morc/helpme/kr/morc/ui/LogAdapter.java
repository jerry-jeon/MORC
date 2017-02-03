package morc.helpme.kr.morc.ui;

import android.support.transition.TransitionManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import morc.helpme.kr.morc.R;
import morc.helpme.kr.morc.model.LogInfo;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

  private RecyclerView recyclerView;
  private List<LogInfo> logInfoList;
  private int expandedPosition = -1;

  public LogAdapter(RecyclerView recyclerView) {
    this(new ArrayList<LogInfo>());
    this.recyclerView = recyclerView;
  }

  public LogAdapter(List<LogInfo> logInfoList) {
    this.logInfoList = logInfoList;
  }

  public void addLogInfo(LogInfo logInfo) {
    logInfoList.add(logInfo);
    notifyItemInserted(getItemCount() - 1);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logging, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  @Override public void onBindViewHolder(ViewHolder holder, final int position) {
    LogInfo logInfo = logInfoList.get(position);
    holder.titleTextView.setText(logInfo.title);
    holder.dateTextView.setText(logInfo.date + " - " + logInfo.type);
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
    return logInfoList.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_title) TextView titleTextView;
    @BindView(R.id.text_date) TextView dateTextView;
    @BindView(R.id.button_expand) AppCompatButton expandButton;
    @BindView(R.id.text_exception) TextView exceptionTextView;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

}
