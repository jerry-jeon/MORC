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
import morc.helpme.kr.morc.model.Route;

public class RouteInfoAdapter extends RecyclerView.Adapter<RouteInfoAdapter.ViewHolder> {

  private RealmResults<Route> routeRealmResults;
  private RouteItemClicekListner routeItemClicekListner;

  public RouteInfoAdapter(RealmResults<Route> routeRealmResults) {
    this.routeRealmResults = routeRealmResults;
  }

  public void setRouteItemClicekListner(RouteItemClicekListner routeItemClicekListner) {
    this.routeItemClicekListner = routeItemClicekListner;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Route route = routeRealmResults.get(position);
    holder.titleTextView.setText(route.title);
    holder.infoTextView.setText(route.from + ", " + route.regex);
    holder.enabledSwitch.setChecked(route.enabled);
  }

  @Override public int getItemCount() {
    return routeRealmResults.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_title) TextView titleTextView;
    @BindView(R.id.text_info) TextView infoTextView;
    @BindView(R.id.switch_enabled) SwitchCompat enabledSwitch;

    @OnCheckedChanged(R.id.switch_enabled) void onCheckChange(boolean isChecked) {
      Realm realm = Realm.getDefaultInstance();
      realm.beginTransaction();
      routeRealmResults.get(getAdapterPosition()).enabled = isChecked;
      realm.commitTransaction();
    }

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          routeItemClicekListner.onClickRouteInfo(getAdapterPosition(), routeRealmResults.get(getAdapterPosition()));
        }
      });
    }
  }

}
