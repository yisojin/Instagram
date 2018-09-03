package kr.hs.dgsw.instagram.Network;

import java.util.Optional;

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
import retrofit.http.PUT;

public interface Network {

    @POST("/user")
    Call<ResponseFormat> join(@Body UserModel userModel);

    @GET("/user")
    Call<ResponseFormat> login(@Header("account") String account, @Header("password") String password);

    @POST("/board")
    Call<ResponseFormat> post(@Body BoardModel boardModel);

    @GET("/board")
    Call<ResponseListFormat> list();

    @GET("/board/view")
    Call<ResponseFormat> view(@Header("id") int idx);

    @DELETE("/board")
    Call<ResponseFormat> delete(@Body BoardModel boardModel);
    @PUT("/board")
    Call<ResponseFormat> update(@Header("id") int idx,@Body BoardModel boardModel);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.80.161.183:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
