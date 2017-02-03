package morc.helpme.kr.morc.ui;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import morc.helpme.kr.morc.R;
import morc.helpme.kr.morc.model.RouteInfo;

public class RoutingAdapter extends RecyclerView.Adapter<RoutingAdapter.ViewHolder> {

  private List<RouteInfo> routeInfoList;

  public RoutingAdapter() {
    this(new ArrayList<RouteInfo>());
  }

  public RoutingAdapter(List<RouteInfo> routeInfoList) {
    this.routeInfoList = routeInfoList;
  }

  public void addRouteInfo(RouteInfo routeInfo) {
    routeInfoList.add(routeInfo);
    notifyItemInserted(getItemCount() - 1);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_routing, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    RouteInfo routeInfo = routeInfoList.get(position);
    holder.titleTextView.setText(routeInfo.title);
    holder.infoTextView.setText(routeInfo.from + ", " + routeInfo.regex);
    holder.enabledSwitch.setChecked(routeInfo.enabled);
  }

  @Override public int getItemCount() {
    return routeInfoList.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_title) TextView titleTextView;
    @BindView(R.id.text_info) TextView infoTextView;
    @BindView(R.id.switch_enabled) SwitchCompat enabledSwitch;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

}
