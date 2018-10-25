package kr.hs.dgsw.instagram.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

import kr.hs.dgsw.instagram.Model.BoardModel;
import kr.hs.dgsw.instagram.Model.FileModel;
import kr.hs.dgsw.instagram.Model.ResponseBoardFormat;
import kr.hs.dgsw.instagram.Model.ResponseFormat;
import kr.hs.dgsw.instagram.Network.Network;
import kr.hs.dgsw.instagram.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBoardActivity extends AppCompatActivity {

    Network network;
    TextView tvTitle, tvContent;
    CheckBox cbLike;
    BoardModel board;
    SharedPreferences sharedPreferences;
    Button btnDelete, btnUpdate;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_board);
        getSupportActionBar().hide();

        imageView = findViewById(R.id.imageView);

        network = Network.retrofit.create(Network.class);

        final Intent intent = getIntent();
        sharedPreferences = getSharedPreferences("loginSetting", MODE_PRIVATE);

        String idx = intent.getStringExtra("id");
        final int id = Integer.parseInt(idx);

        //수정 후 id 값이 null 로 받아와짐

        tvTitle = (TextView) findViewById(R.id.tvTitle);

        cbLike = (CheckBox) findViewById(R.id.cbLike);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);



        Call<ResponseBoardFormat> request = network.view(id);
        request.enqueue(new Callback<ResponseBoardFormat>() {
            @Override
            public void onResponse(Call<ResponseBoardFormat> call, Response<ResponseBoardFormat> response) {
                board = response.body().getData();
//                if(board.getLikesUser() != null){
//                    cbLike.setChecked(true);
//                }else{
//                    cbLike.setChecked(false);
//                }
                Log.e("obj", board.toString());

                tvTitle.setText(board.getTitle());

//                if (board.isLike()) {
//                    cbLike.setChecked(true);
//                } else {
//                    cbLike.setChecked(false);
//                }
            }

            @Override
            public void onFailure(Call<ResponseBoardFormat> call, Throwable t) {

            }
        });

        Call<ResponseFormat> request2 = network.downloadImage(id);
        request2.enqueue(new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                Log.e("response",response.body().getData().toString());

                FileModel fm =  (FileModel) response.body().getData();

                ///storage/emulated/0/Pictures/facebook/
                ///storage/emulated/0/DCIM/Camera/
                ///storage/emulated/0/Download/
                ////storage/emulated/0/camtest/

                String imagePath = "/storage/emulated/0/Pictures/facebook/"+fm.getOriginalName();

                Uri imageURI = Uri.fromFile(new File(imagePath));
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(imageURI);
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    imageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseFormat> call, Throwable t) {

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<String> request = network.delete(id);
                request.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("response", response.message().toString());
                        Intent intent1 = new Intent(ViewBoardActivity.this, BoardListActivity.class);
                        startActivity(intent1);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ViewBoardActivity.this, BoardUpdateActivity.class);
                intent1.putExtra("title", board.getTitle());
                intent1.putExtra("id", id);
                intent1.putExtra("likes", board.getLikes());
                intent1.putExtra("isLike", board.isLike());
                startActivity(intent1);
            }
        });

//        cbLike.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Log.e("board", board.getId());
//                Log.e("user", sharedPreferences.getString("name", ""));
//
//                board.setLikesUser(sharedPreferences.getString("name", ""));
//                board.setLike(true);
//                board.setLikes(board.getLikes() + 1);
//
//                Call<ResponseBoardFormat> request = network.like(sharedPreferences.getString("account",""), board);
//                request.enqueue(new Callback<ResponseBoardFormat>() {
//                    @Override
//                    public void onResponse(Response<ResponseBoardFormat> response, Retrofit retrofit) {
//                        Log.e("response", response.body().getData().toString());
//
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//
//                    }
//                });
//
//            }
//        });

    }
}
