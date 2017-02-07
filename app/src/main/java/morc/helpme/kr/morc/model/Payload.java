package morc.helpme.kr.morc.model;

public class Payload {
  String subject;
  String text;

  public Payload(String subject, String text) {
    this.subject = subject;
    this.text = text;
  }

  public Payload(String text) {
    this.text = text;
  }
}
