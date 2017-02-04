package morc.helpme.kr.morc.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import morc.helpme.kr.morc.Log;
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

    if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
      Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
      SmsMessage[] msgs;
      String msg_from;
      if (bundle != null){
        //---retrieve the SMS message received---
        try{
          Object[] pdus = (Object[]) bundle.get("pdus");
          msgs = new SmsMessage[pdus.length];
          for(int i=0; i<msgs.length; i++){
            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            msg_from = msgs[i].getOriginatingAddress();
            String msgBody = msgs[i].getMessageBody();
            Toast.makeText(context, msgBody, Toast.LENGTH_LONG).show();
            helpmeService.test().enqueue(new Callback<ResponseBody>() {
              @Override
              public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("code : " + response.code());
              }

              @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
              }
            });
          }
        }catch(Exception e){
          e.printStackTrace();
        }
      }
    }
  }

  private void initializeRetrofit(Context context) {

    //TODO 통신 확인 해볼 필요 있음
    if(retrofit == null) {
      retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.11:9000").build();
    }

    helpmeService = retrofit.create(HelpmeService.class);

    /*
    helpmeService.test().enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d("code : " + response.code());
      }

      @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
      }
    });
    */
  }
}
