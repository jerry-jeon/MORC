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

public class RouteListFragment extends Fragment {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  private RoutingAdapter routingAdapter;

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
    recyclerView.setAdapter(routingAdapter);

    List<String> urlList = new ArrayList<>(2);
    urlList.add("url1");
    urlList.add("url2");
    routingAdapter.addRouteInfo(new RouteInfo("des", "010", "sub", urlList, true));
  }

  @OnClick(R.id.fab) void onClickFAB() {
    startActivityForResult(new Intent(getActivity(), RouteActivity.class), 0);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode == RESULT_OK && data.getParcelableExtra("Route") != null) {
      routingAdapter.addRouteInfo((RouteInfo) data.getParcelableExtra("Route"));
    }
  }
}
