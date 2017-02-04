package morc.helpme.kr.morc;

import android.app.Application;
import io.realm.Realm;

public class HelpmeApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    Realm.init(getApplicationContext());
  }
}
