package morc.helpme.kr.morc.model;

public class Trigger {
  String title;
  String from;
  String regex;

  public Trigger(Route route) {
    this.title = route.title;
    this.from = route.from;
    this.regex = route.regex;
  }
}
