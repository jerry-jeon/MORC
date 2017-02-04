package morc.helpme.kr.morc.ui;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.realm.Realm;
import io.realm.RealmResults;
import morc.helpme.kr.morc.R;
import morc.helpme.kr.morc.model.RouteInfo;

public class RouteInfoAdapter extends RecyclerView.Adapter<RouteInfoAdapter.ViewHolder> {

  private RealmResults<RouteInfo> routeInfoRealmResults;
  private RouteItemClicekListner routeItemClicekListner;

  public RouteInfoAdapter(RealmResults<RouteInfo> routeInfoRealmResults) {
    this.routeInfoRealmResults = routeInfoRealmResults;
  }

  public void setRouteItemClicekListner(RouteItemClicekListner routeItemClicekListner) {
    this.routeItemClicekListner = routeItemClicekListner;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    RouteInfo routeInfo = routeInfoRealmResults.get(position);
    holder.titleTextView.setText(routeInfo.title);
    holder.infoTextView.setText(routeInfo.from + ", " + routeInfo.regex);
    holder.enabledSwitch.setChecked(routeInfo.enabled);
  }

  @Override public int getItemCount() {
    return routeInfoRealmResults.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_title) TextView titleTextView;
    @BindView(R.id.text_info) TextView infoTextView;
    @BindView(R.id.switch_enabled) SwitchCompat enabledSwitch;

    @OnCheckedChanged(R.id.switch_enabled) void onCheckChange(boolean isChecked) {
      Realm realm = Realm.getDefaultInstance();
      realm.beginTransaction();
      routeInfoRealmResults.get(getAdapterPosition()).enabled = isChecked;
      realm.commitTransaction();
    }

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          routeItemClicekListner.onClickRouteInfo(getAdapterPosition(), routeInfoRealmResults.get(getAdapterPosition()));
        }
      });
    }
  }

}
