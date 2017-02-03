package morc.helpme.kr.morc.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

  private static final int PAGE_COUNT = 2;

  public PagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new RoutingFragment();
      case 1:
        return new LogFragment();
      default:
        return null;
    }
  }

  @Override public int getCount() {
    return PAGE_COUNT;
  }
}
