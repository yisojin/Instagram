package kr.hs.dgsw.instagram.Network;

import java.util.Optional;

import kr.hs.dgsw.instagram.Model.BoardModel;
import kr.hs.dgsw.instagram.Model.ResponseBoardFormat;
import kr.hs.dgsw.instagram.Model.ResponseFormat;
import kr.hs.dgsw.instagram.Model.ResponseListFormat;
import kr.hs.dgsw.instagram.Model.ResponseUserFormat;
import kr.hs.dgsw.instagram.Model.UserModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.PartMap;

public interface Network {

    @POST("/user")
    Call<ResponseFormat> join(@Body UserModel userModel);

    @GET("/user")
    Call<ResponseUserFormat> login(@Header("account") String account, @Header("password") String password);

    @POST("/board")
    Call<ResponseFormat> post(@Body BoardModel boardModel);

    @GET("/board")
    Call<ResponseListFormat> list();

    @GET("/board/view")
    Call<ResponseBoardFormat> view(@Header("id") int idx);

    @GET("/board/like")
    Call<ResponseFormat> viewlike(@Header("boardId") int idx);

    @DELETE("/board")
    Call<String> delete(@Header("idx") int idx);

    @PUT("/board")
    Call<ResponseBoardFormat> update(@Header("id") int idx, @Body BoardModel boardModel);

    @PUT("/like")
    Call<ResponseBoardFormat> like(@Header("account") String userId, @Body BoardModel boardModel);

    @Multipart
    @POST("/file")
    Call<ResponseFormat> uploadImage(@Part("file\"; filename=\"photo.png") MultipartBody.Part image);

    @Multipart
    @GET("/file")
    Call<ResponseFormat> downloadImage();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.80.161.183:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    //school 10.80.161.183
    //exco stabucks 172.30.124.132
    //home 172.30.1.37
    //외할머니댁 172.30.1.27
}
