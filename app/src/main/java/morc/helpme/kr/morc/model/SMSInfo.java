package morc.helpme.kr.morc.model;

public class SMSInfo {
  public Trigger trigger;
  public Envelope envelope;
  public Payload payload;
  public long timestamp;

  public SMSInfo(Trigger trigger, Envelope envelope, Payload payload, long timestamp) {
    this.trigger = trigger;
    this.envelope = envelope;
    this.payload = payload;
    this.timestamp = timestamp;
  }

  @Override public String toString() {
    return "SMSInfo{" +
        "trigger=" + trigger +
        ", envelope=" + envelope +
        ", payload=" + payload +
        ", timestamp=" + timestamp +
        '}';
  }
}
