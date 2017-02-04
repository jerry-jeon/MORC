package morc.helpme.kr.morc.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface HelpmeService {
  @Headers("Content-Type: application/json")
  @POST Call<ResponseBody> dynamic(@Url String url, @Header("Authorization") String authorization);
}
