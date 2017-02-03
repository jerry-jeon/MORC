package morc.helpme.kr.morc;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.io.IOException;
import morc.helpme.kr.morc.retrofit.HelpmeService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

  private Retrofit retrofit;
  private HelpmeService helpmeService;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    initalizeLibraries();

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
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
    });
  }

  private void initalizeLibraries() {
    initializeRetrofit();
    initalizeDexter();
  }

  private void initializeRetrofit() {
    //TODO 통신 확인 해볼 필요 있음
    retrofit = new Retrofit.Builder()
        .baseUrl("http://192.168.1.11:9000")
        .build();

    helpmeService = retrofit.create(HelpmeService.class);
  }

  private void initalizeDexter() {
    Dexter.withActivity(this)
        .withPermission(Manifest.permission.RECEIVE_SMS)
        .withListener(new PermissionListener() {
          @Override public void onPermissionGranted(PermissionGrantedResponse response) {

          }

          @Override public void onPermissionDenied(PermissionDeniedResponse response) {

          }

          @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
              PermissionToken token) {

          }
        })
        .check();

  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
