package morc.helpme.kr.morc.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;
import morc.helpme.kr.morc.Log;
import morc.helpme.kr.morc.R;
import morc.helpme.kr.morc.model.RouteInfo;

public class RouteActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.layout_urls) LinearLayout layout;
  @BindView(R.id.edit_title) TextInputEditText editTitle;
  @BindView(R.id.edit_from) TextInputEditText editFrom;
  @BindView(R.id.edit_regex) TextInputEditText editRegex;

  private List<TextInputEditText> urlViewList;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_route);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    /*
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        finish();
      }
    });
    */

    urlViewList = new ArrayList<>();
    addUrlView();
  }

  @OnClick(R.id.button_add_url) void onClickButton() {
    addUrlView();
  }

  private void addUrlView() {
    LayoutInflater inflater = (LayoutInflater) getSystemService (Context.LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.layout_url, null);
    urlViewList.add((TextInputEditText) view.findViewById(R.id.edit_url));
    layout.addView(view);
  }

  private List<String> getUrlsFromViewList() {
    List<String> urlList = new ArrayList<>(urlViewList.size());
    for(int i = 0; i < urlViewList.size(); i++) {
      urlList.add(urlViewList.get(i).getText().toString());
    }
    return urlList;
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_route, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_save:
        //TODO 예외처리
        RouteInfo routeInfo = new RouteInfo(editTitle.getText().toString(),
            editFrom.getText().toString(), editRegex.getText().toString(), getUrlsFromViewList(), true);

        Intent intent = new Intent();
        intent.putExtra("Route", routeInfo);
        setResult(RESULT_OK, intent);
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
