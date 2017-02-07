package morc.helpme.kr.morc.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import io.realm.Realm;
import io.realm.RealmResults;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import morc.helpme.kr.morc.Log;
import morc.helpme.kr.morc.model.Envelope;
import morc.helpme.kr.morc.model.LogInfo;
import morc.helpme.kr.morc.model.Payload;
import morc.helpme.kr.morc.model.Route;
import morc.helpme.kr.morc.model.SMSInfo;
import morc.helpme.kr.morc.model.Trigger;
import morc.helpme.kr.morc.retrofit.HelpmeService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MMSReceiver extends BroadcastReceiver {

  private Retrofit retrofit;
  private HelpmeService helpmeService;

  @Override public void onReceive(Context context, Intent intent) {
    initializeRetrofit();
    parseMMS(context);
  }

  private void parseMMS(Context context) {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    ContentResolver contentResolver = context.getContentResolver();
    final String[] projection = new String[] { "_id", "sub", "date"};
    Uri uri = Uri.parse("content://mms");
    Cursor cursor = contentResolver.query(uri, null, null, null, "_id desc limit 1");
    if (cursor.getCount() == 0) {
      cursor.close();
      return;
    }


    cursor.moveToFirst();
    for(int i = 0 ; i < cursor.getColumnCount(); i++) {
      Log.d("col : " + cursor.getColumnName(i));
      int type = cursor.getType(i);
      switch (type) {
        case Cursor.FIELD_TYPE_BLOB:
          Log.d("blob : " + cursor.getBlob(i));
          break;
        case Cursor.FIELD_TYPE_FLOAT:
          Log.d("float : " + cursor.getFloat(i));
          break;
        case Cursor.FIELD_TYPE_INTEGER:
          Log.d("int : " + cursor.getInt(i));
          break;
        case Cursor.FIELD_TYPE_STRING:
          Log.d("string : " + cursor.getString(i));
          break;
      }
    }

    String id = cursor.getString(cursor.getColumnIndex("_id"));
    byte[] bytes = cursor.getString(cursor.getColumnIndex("sub")).getBytes();
    Log.d("s1 : " + new String(bytes, Charset.forName("EUC-KR")));
    Log.d("s2 : " + new String(bytes, Charset.forName("KSC5601")));
    Log.d("s3 : " + new String(bytes, Charset.forName("ISO-8859-1")));
    for(int i = 0; i < bytes.length; i++) {
      Log.d("byte : " + bytes[i]);
    }
    //Log.d("s3 : " + new String(bytes, Charset.forName(""));
    long timestamp = cursor.getInt(cursor.getColumnIndex("date"));

    cursor.close();

    String msgFrom = parseNumber(context, id);
    String msgBody = parseMessage(context, id);

    final Realm realm = Realm.getDefaultInstance();
    RealmResults<Route>
        routeRealmResults = realm.where(Route.class).equalTo("enabled", true).findAll();

    for(int j = 0; j < routeRealmResults.size(); j++) {
      final Route route = routeRealmResults.get(j);
      if(route.satisfyCondition(msgFrom, msgBody)) {
        for(int k = 0; k < route.urlList.size(); k++) {
          Trigger trigger = new Trigger(route);
          Envelope envelope = new Envelope(msgFrom);
          Payload payload = new Payload(msgBody);

          SMSInfo smsInfo = new SMSInfo(trigger, envelope, payload, timestamp, tagToArray(route.tag));

          helpmeService.dynamic(route.urlList.get(k).str, route.authorization, smsInfo).enqueue(new Callback<ResponseBody>() {
            @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              Realm realm1 = Realm.getDefaultInstance();
              realm1.beginTransaction();

              LogInfo logInfo = realm1.createObject(LogInfo.class);
              logInfo.initialize(route.title, formattedDate(), String.valueOf(response.code()), null);

              realm1.commitTransaction();
            }

            @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
              Realm realm1 = Realm.getDefaultInstance();
              realm1.beginTransaction();

              LogInfo logInfo = realm1.createObject(LogInfo.class);
              logInfo.initialize(route.title, formattedDate(), LogInfo.ERROR, t.toString());

              realm1.commitTransaction();
            }
          });
        }
      }
    }
  }

  private String parseNumber(Context context, String id) {
    String result = null;

    Uri uri = Uri.parse(MessageFormat.format("content://mms/{0}/addr", id));
    String[] projection = new String[] { "address" };
    String selection = "msg_id = " + id + " and type = 137";// type=137은 발신자
    String[] selectionArgs = new String[] { id };

    Cursor cursor = context.getContentResolver().query(uri, projection, selection, null, "_id asc limit 1");

    if (cursor.getCount() == 0) {
      cursor.close();
      return result;
    }

    cursor.moveToFirst();
    result = cursor.getString(cursor.getColumnIndex("address"));
    cursor.close();

    return result;
  }

  private String parseMessage(Context context, String id) {
    String result = null;

    // 조회에 조건을 넣게되면 가장 마지막 한두개의 mms를 가져오지 않는다.
    Cursor cursor = context.getContentResolver().query(Uri.parse("content://mms/part"), new String[] { "mid", "_id", "ct", "_data", "text" }, null, null, null);

    if (cursor.getCount() == 0) {
      cursor.close();
      return result;
    }

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {

      String mid = cursor.getString(cursor.getColumnIndex("mid"));
      if (id.equals(mid)) {
        String partId = cursor.getString(cursor.getColumnIndex("_id"));
        String type = cursor.getString(cursor.getColumnIndex("ct"));
        if ("text/plain".equals(type)) {
          String data = cursor.getString(cursor.getColumnIndex("_data"));

          if (TextUtils.isEmpty(data))
            result = cursor.getString(cursor.getColumnIndex("text"));
          else
            result = parseMessageWithPartId(context, partId);
        }
      }
      cursor.moveToNext();
    }
    cursor.close();

    return result;
  }


  private String parseMessageWithPartId(Context context, String id) {
    Uri partURI = Uri.parse("content://mms/part/" + id);
    InputStream is = null;
    StringBuilder sb = new StringBuilder();
    try {
      is = context.getContentResolver().openInputStream(partURI);
      if (is != null) {
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader reader = new BufferedReader(isr);
        String temp = reader.readLine();
        while (!TextUtils.isEmpty(temp)) {
          sb.append(temp);
          temp = reader.readLine();
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if (is != null) {
        try {
          is.close();
        }
        catch (IOException e) {
        }
      }
    }
    return sb.toString();
  }

  private void initializeRetrofit() {
    if(retrofit == null) {
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

      retrofit = new Retrofit.Builder()
          .baseUrl("http://localhost")
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }

    helpmeService = retrofit.create(HelpmeService.class);
  }

  private String formattedDate() {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
    return format.format(new Date());
  }

  public String[] tagToArray(String tag) {
    String[] results = tag.split(",");
    for(int i = 0; i < results.length; i++) {
      results[i] = results[i].trim();
    }
    return results;
  }

}
