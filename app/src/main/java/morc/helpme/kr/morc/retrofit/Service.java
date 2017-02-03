package morc.helpme.kr.morc.retrofit;

import retrofit2.http.GET;

public interface Service {
  @GET("http://localhost:9000") void test();
}
