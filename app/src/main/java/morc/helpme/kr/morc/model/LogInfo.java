package morc.helpme.kr.morc.model;

import android.graphics.Color;
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

  public int getColor() {
    if(type.startsWith("20")) {
      return Color.rgb(50, 205, 50);
    } else if(type.equals(ERROR)) {
      return Color.RED;
    } else {
      return Color.rgb(218, 165,32);
    }
  }
}
