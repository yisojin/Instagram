package kr.hs.dgsw.instagram.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kr.hs.dgsw.instagram.Model.BoardModel;
import kr.hs.dgsw.instagram.Model.ResponseFormat;
import kr.hs.dgsw.instagram.Network.Network;
import kr.hs.dgsw.instagram.R;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class BoardAddActivity extends AppCompatActivity {

    Button btnSend;
    EditText texttitle, textContent, textWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_add);

        btnSend = (Button) findViewById(R.id.btnAdd);

        textWriter = (EditText) findViewById(R.id.etWriter);
        texttitle = (EditText) findViewById(R.id.etTitle);
        textContent = (EditText) findViewById(R.id.etContent);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String writer = textWriter.getText().toString();
                String content = textContent.getText().toString();
                String title = texttitle.getText().toString();

                BoardModel bm = new BoardModel();
                bm.setTitle(title);
                bm.setContent(content);
                bm.setWriter(writer);

                Network network = Network.retrofit.create(Network.class);
                Call<ResponseFormat> call = network.post(bm);
                call.enqueue(new Callback<ResponseFormat>() {
                    @Override
                    public void onResponse(Response<ResponseFormat> response, Retrofit retrofit) {
                        Log.e("result", response.body().toString());

                        Intent intent = new Intent(BoardAddActivity.this, BoardListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("error", t.getMessage());
                    }
                });
            }
        });
    }
}
