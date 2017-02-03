package morc.helpme.kr.morc;

public class Log {

  private static final String tag = "Helpme";

  public static void d(String message) {
    android.util.Log.d(tag, message);
  }

}
