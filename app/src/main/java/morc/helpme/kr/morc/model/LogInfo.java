package morc.helpme.kr.morc.model;

public class LogInfo {

  public static final String ERROR = "Error/Exception";

  public String title;
  public String date;
  public String type;
  public String exception;

  public LogInfo(String title, String date, String type, String exception) {
    this.title = title;
    this.date = date;
    this.type = type;
    this.exception = exception;
  }
}
