package kr.hs.dgsw.instagram.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kr.hs.dgsw.instagram.Model.BoardModel;
import kr.hs.dgsw.instagram.Model.ResponseBoardFormat;
import kr.hs.dgsw.instagram.Network.Network;
import kr.hs.dgsw.instagram.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardUpdateActivity extends AppCompatActivity {

    EditText etTitle, etContent;
    Button btnSend;
    Network network;
    SharedPreferences sharedPreferences;
    boolean isCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_update);
        getSupportActionBar().hide();

        final Intent intent = getIntent();
        sharedPreferences = getSharedPreferences("loginSetting", MODE_PRIVATE);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        btnSend = (Button) findViewById(R.id.btnSend);

        network = Network.retrofit.create(Network.class);

        etTitle.setText(intent.getStringExtra("title"));
        etContent.setText(intent.getStringExtra("content"));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idx = intent.getIntExtra("id", 0);
                String title = etTitle.getText().toString();
                int id = Integer.parseInt(sharedPreferences.getString("id", ""));
                String likeUser = sharedPreferences.getString("name", "");
                boolean isLike = intent.getBooleanExtra("isLike", false);
                int likes = intent.getIntExtra("likes", 0);

                BoardModel bm = new BoardModel();
                bm.setId(Integer.toString(idx));
                bm.setTitle(title);
                bm.setU_id(id);
                bm.setLike(isLike);
                bm.setLikes(likes);
                bm.setLikesUser(likeUser);

                Call<ResponseBoardFormat> request = network.update(idx, bm);
                request.enqueue(new Callback<ResponseBoardFormat>() {
                    @Override
                    public void onResponse(Call<ResponseBoardFormat> call, Response<ResponseBoardFormat> response) {
                        BoardModel b = response.body().getData();
                        Log.e("response", response.body().toString());
                        Intent intent1 = new Intent(BoardUpdateActivity.this, ViewBoardActivity.class);
                        intent1.putExtra("id", b.getId());
                        startActivityForResult(intent1, 1);
                    }

                    @Override
                    public void onFailure(Call<ResponseBoardFormat> call, Throwable t) {

                    }
                });

            }
        });

    }
}
