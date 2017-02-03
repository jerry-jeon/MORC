package morc.helpme.kr.morc.ui;

import android.content.Intent;
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
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;
import morc.helpme.kr.morc.R;
import morc.helpme.kr.morc.model.RouteInfo;

import static android.app.Activity.RESULT_OK;

public class RouteListFragment extends Fragment implements RouteItemClicekListner {

  private static final int REQUEST_NEW = 0;
  private static final int REQUEST_EDIT = 1;

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  private RoutingAdapter routingAdapter;
  private int clickedPosition;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_route_list, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    setupUI();
  }

  private void setupUI() {
    // use this setting to improve performance if you know that changes
    // in content do not change the layout size of the RecyclerView
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    routingAdapter = new RoutingAdapter();
    routingAdapter.setRouteItemClicekListner(this);
    recyclerView.setAdapter(routingAdapter);

    List<String> urlList = new ArrayList<>(2);
    urlList.add("url1");
    urlList.add("url2");
    routingAdapter.addRouteInfo(new RouteInfo("des", "010", "sub", urlList, true));
  }

  @OnClick(R.id.fab) void onClickFAB() {
    startActivityForResult(new Intent(getActivity(), RouteActivity.class), REQUEST_NEW);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode == RESULT_OK) {
      RouteInfo routeInfo = data.getParcelableExtra("Route");
      switch (requestCode) {
        case REQUEST_NEW:
          if(routeInfo != null)
            routingAdapter.addRouteInfo(routeInfo);
          break;
        case REQUEST_EDIT:
          if(data.getIntExtra("remove", 0) == 1)
            routingAdapter.remove(clickedPosition);
          else if(routeInfo != null)
            routingAdapter.setRouteInfo(clickedPosition, routeInfo);
          break;
      }
    }
  }

  @Override public void onClickRouteInfo(int poisition, RouteInfo routeInfo) {
    clickedPosition = poisition;
    Intent intent = new Intent(getActivity(), RouteActivity.class);
    intent.putExtra("Route", routeInfo);
    startActivityForResult(intent, REQUEST_EDIT);
  }
}
