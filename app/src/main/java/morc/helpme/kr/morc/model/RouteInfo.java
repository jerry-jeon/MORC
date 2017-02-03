package morc.helpme.kr.morc.model;

import java.util.List;

public class RouteInfo {
  public String title;
  public String from;
  public String regex;
  public List<String> urlList;
  public boolean enabled;

  public RouteInfo(String title, String from, String regex, boolean enabled) {
    this.title = title;
    this.from = from;
    this.regex = regex;
    this.enabled = enabled;
  }


}
