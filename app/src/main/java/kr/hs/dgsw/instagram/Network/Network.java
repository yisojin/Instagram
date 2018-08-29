package kr.hs.dgsw.instagram.Network;

import kr.hs.dgsw.instagram.Model.BoardModel;
import kr.hs.dgsw.instagram.Model.ResponseFormat;
import kr.hs.dgsw.instagram.Model.ResponseListFormat;
import kr.hs.dgsw.instagram.Model.UserModel;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

public interface Network {

    @POST("/user")
    Call<ResponseFormat> join(@Body UserModel userModel);

    @GET("/user")
    Call<ResponseFormat> login(@Header("account") String account, @Header("password") String password);

    @POST("/board")
    Call<ResponseFormat> post(@Body BoardModel boardModel);

    @GET("/board")
    Call<ResponseListFormat> list();

    @DELETE("/board")
    Call<ResponseFormat> delete(@Body BoardModel boardModel);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.80.161.183:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
