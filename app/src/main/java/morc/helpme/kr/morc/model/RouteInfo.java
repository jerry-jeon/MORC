package morc.helpme.kr.morc.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RouteInfo extends RealmObject {
  @PrimaryKey public int id;
  public String title;
  public String from;
  public String regex;
  public RealmList<RealmString> urlList;
  public boolean enabled;

  public void initialize(String title, String from, String regex, RealmList<RealmString> urlList, boolean enabled) {
    this.title = title;
    this.from = from;
    this.regex = regex;
    this.urlList = urlList;
    this.enabled = enabled;
  }
}
