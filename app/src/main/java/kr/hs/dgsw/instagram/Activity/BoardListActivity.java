package kr.hs.dgsw.instagram.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.instagram.Model.BoardModel;
import kr.hs.dgsw.instagram.Model.ResponseListFormat;
import kr.hs.dgsw.instagram.Network.Network;
import kr.hs.dgsw.instagram.R;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class BoardListActivity extends AppCompatActivity {

    ImageButton btn1, btn2, btn3, btn4, btn5;
    Network request;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);
        getSupportActionBar().hide();

        final ListView listView = (ListView) findViewById(R.id.lvBoard);

        btn1 = findViewById(R.id.ibtnHome);
        btn2 = findViewById(R.id.ibtnSearch);
        btn3 = findViewById(R.id.ibtnAdd);
        btn4 = findViewById(R.id.ibtnLike);
        btn5 = findViewById(R.id.ibtnUser);

        request = Network.retrofit.create(Network.class);

        Call<ResponseListFormat> listRequest = request.list();
        listRequest.enqueue(new Callback<ResponseListFormat>() {

            @Override
            public void onResponse(Response<ResponseListFormat> response, Retrofit retrofit) {
                ArrayList<String> list = new ArrayList<String>();

                ArrayList<BoardModel> listBoard = new ArrayList<BoardModel>();
                for (BoardModel board : response.body().getData()) {
                    listBoard.add(board);
                    list.add(board.getTitle());
                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, R.id.text1, list);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("select",parent.toString());
            }
        });



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardListActivity.this, BoardAddActivity.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
