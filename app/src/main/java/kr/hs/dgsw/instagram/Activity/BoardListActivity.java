package kr.hs.dgsw.instagram.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        Network network = Network.retrofit.create(Network.class);
        Call<ResponseListFormat> call = network.list();
        call.enqueue(new Callback<ResponseListFormat>() {

            ListView listView =(ListView)findViewById(R.id.lvBoard);

            @Override
            public void onResponse(Response<ResponseListFormat> response, Retrofit retrofit) {
                ArrayList<String> list = new ArrayList<String>();
                ArrayAdapter<String> adapter;

                List<BoardModel> lst = new ArrayList<BoardModel>();
                for (BoardModel n : response.body().getData()) {
                    lst.add(n);
                    list.add(n.getContent());
                    Log.e("data", n.getTitle().toString());
                }

                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, R.id.text1, list);

                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
