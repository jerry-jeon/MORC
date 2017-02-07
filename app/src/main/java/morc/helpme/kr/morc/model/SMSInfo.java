package morc.helpme.kr.morc.model;

public class SMSInfo {
  public Trigger trigger;
  public Envelope envelope;
  public Payload payload;
  public long timestamp;
  public String[] tags;

  public SMSInfo(Trigger trigger, Envelope envelope, Payload payload, long timestamp, String[] tags) {
    this.trigger = trigger;
    this.envelope = envelope;
    this.payload = payload;
    this.timestamp = timestamp;
    this.tags = tags;
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
