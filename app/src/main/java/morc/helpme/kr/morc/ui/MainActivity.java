package morc.helpme.kr.morc.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import morc.helpme.kr.morc.Log;
import morc.helpme.kr.morc.R;
import morc.helpme.kr.morc.retrofit.HelpmeService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.tabs) TabLayout tabLayout;
  @BindView(R.id.viewPager) ViewPager viewPager;

  private Retrofit retrofit;
  private HelpmeService helpmeService;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    initializeLibraries();
    initializeUI();
  }

  @OnClick(R.id.fab) void onClickFAB() {
    startActivity(new Intent(this, RouteActivity.class));
  }

  private void initializeLibraries() {
    initializeRetrofit();
    initializeDexter();
  }

  private void initializeRetrofit() {
    //TODO 통신 확인 해볼 필요 있음
    retrofit = new Retrofit.Builder()
        .baseUrl("http://192.168.1.11:9000")
        .build();

    helpmeService = retrofit.create(HelpmeService.class);

    /*
    helpmeService.test().enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d("code : " + response.code());
      }

      @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
      }
    });
    */
  }

  private void initializeDexter() {
    Dexter.withActivity(this)
        .withPermission(Manifest.permission.RECEIVE_SMS)
        .withListener(new PermissionListener() {
          @Override public void onPermissionGranted(PermissionGrantedResponse response) {

          }

          @Override public void onPermissionDenied(PermissionDeniedResponse response) {

          }

          @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
              PermissionToken token) {

          }
        })
        .check();

  }

  private void initializeUI() {
    initializeTabLayout();
    initializeViewPager();
  }

  private void initializeTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText("Routing"));
    tabLayout.addTab(tabLayout.newTab().setText("Log"));
  }

  private void initializeViewPager() {
    final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(adapter);
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

}
