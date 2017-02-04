package morc.helpme.kr.morc.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import morc.helpme.kr.morc.R;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.tabs) TabLayout tabLayout;
  @BindView(R.id.viewPager) ViewPager viewPager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    initializeDexter();
    initializeUI();
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
