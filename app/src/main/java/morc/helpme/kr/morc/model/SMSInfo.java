package morc.helpme.kr.morc.model;

import java.sql.Timestamp;

public class SMSInfo {
  public Trigger trigger;
  public Envelope envelope;
  public Payload payload;
  public Timestamp timestamp;

  public SMSInfo(Trigger trigger, Envelope envelope, Payload payload, Timestamp timestamp) {
    this.trigger = trigger;
    this.envelope = envelope;
    this.payload = payload;
    this.timestamp = timestamp;
  }
}
