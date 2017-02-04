package morc.helpme.kr.morc.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface HelpmeService {
  @GET("/users/me") Call<ResponseBody> test();
  @POST Call<ResponseBody> dynamic(@Url String url);
}
