package morc.helpme.kr.morc.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class RouteInfo implements Parcelable {
  public String title;
  public String from;
  public String regex;
  public List<String> urlList;
  public boolean enabled;

  public static final Parcelable.Creator<RouteInfo> CREATOR
      = new Parcelable.Creator<RouteInfo>() {
    public RouteInfo createFromParcel(Parcel in) {
      return new RouteInfo(in);
    }

    public RouteInfo[] newArray(int size) {
      return new RouteInfo[size];
    }
  };

  public RouteInfo(String title, String from, String regex, boolean enabled) {
    this.title = title;
    this.from = from;
    this.regex = regex;
    this.enabled = enabled;
  }

  public RouteInfo(Parcel parcel) {
    readFromParcel(parcel);
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(title);
    parcel.writeString(from);
    parcel.writeString(regex);
    parcel.writeStringList(urlList);
    parcel.writeBooleanArray(new boolean[] {enabled});
  }

  public void readFromParcel(Parcel parcel) {
    title = parcel.readString();
    from = parcel.readString();
    regex = parcel.readString();
    parcel.readStringList(urlList);
    boolean[] temp = new boolean[1];
    parcel.readBooleanArray(temp);
    enabled = temp[0];
  }
}
