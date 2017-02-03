package morc.helpme.kr.morc.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface HelpmeService {
  @GET("/users/me") Call<ResponseBody> test();
}
