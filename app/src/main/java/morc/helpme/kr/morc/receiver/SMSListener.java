package morc.helpme.kr.morc.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import io.realm.Realm;
import io.realm.RealmResults;
import morc.helpme.kr.morc.Log;
import morc.helpme.kr.morc.model.RouteInfo;
import morc.helpme.kr.morc.retrofit.HelpmeService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SMSListener extends BroadcastReceiver {

  private Retrofit retrofit;
  private HelpmeService helpmeService;

  @Override
  public void onReceive(Context context, Intent intent) {
    initializeRetrofit(context);

    if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
      Bundle bundle = intent.getExtras();
      if (bundle != null) {
        try{
          Object[] pdus = (Object[]) bundle.get("pdus");
          SmsMessage[] messages = new SmsMessage[pdus.length];
          for(int i = 0; i < messages.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
              messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], bundle.getString("format"));
            } else {
              messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }

            String msgFrom = messages[i].getOriginatingAddress();
            String msgBody = messages[i].getMessageBody();

            Realm realm = Realm.getDefaultInstance();
            RealmResults<RouteInfo> routeInfoRealmResults = realm.where(RouteInfo.class).equalTo("enabled", true).findAll();

            for(int j = 0; j < routeInfoRealmResults.size(); j++) {
              RouteInfo routeInfo = routeInfoRealmResults.get(j);
              if(routeInfo.satisfyCondition(msgFrom, msgBody)) {
                for(int k = 0; k < routeInfo.urlList.size(); k++) {
                  helpmeService.dynamic(routeInfo.urlList.get(k).str).enqueue(new Callback<ResponseBody>() {
                    @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                      Log.d("code : " + response.code());
                    }

                    @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
                      t.printStackTrace();
                    }
                  });
                }
              }
            }
          }
        } catch(Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void initializeRetrofit(Context context) {
    //TODO 통신 확인 해볼 필요 있음
    if(retrofit == null) {
      retrofit = new Retrofit.Builder().baseUrl("http://localhost").build();
    }

    helpmeService = retrofit.create(HelpmeService.class);
  }
}
