package morc.helpme.kr.morc.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class LogInfo extends RealmObject {

  @Ignore public static final String ERROR = "Error/Exception";

  public String title;
  public String date;
  public String type;
  public String exception;

  public void initialize(String title, String date, String type, String exception) {
    this.title = title;
    this.date = date;
    this.type = type;
    this.exception = exception;
  }
}
