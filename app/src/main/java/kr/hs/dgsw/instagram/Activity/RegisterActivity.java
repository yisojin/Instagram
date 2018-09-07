package kr.hs.dgsw.instagram.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import kr.hs.dgsw.instagram.Common.IsValid;
import kr.hs.dgsw.instagram.Database.DBManager;
import kr.hs.dgsw.instagram.Model.ResponseFormat;
import kr.hs.dgsw.instagram.Model.UserModel;
import kr.hs.dgsw.instagram.Network.Network;
import kr.hs.dgsw.instagram.R;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    DBManager dbManager;
    private CallbackManager callbackManager;
    IsValid isValid = new IsValid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        final EditText etTel = (EditText) findViewById(R.id.etTel);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etAccount = (EditText) findViewById(R.id.etAccount);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
//        css();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String tel = etTel.getText().toString();
                final String name = etName.getText().toString();
                final String account = etAccount.getText().toString();
                final String password = etPassword.getText().toString();

                UserModel userModel = new UserModel();
                userModel.setName(name);
                userModel.setAccount(account);
                userModel.setPassword(password);
                userModel.setTel(tel);

                final Network network = Network.retrofit.create(Network.class);
                Call<ResponseFormat> call = network.join(userModel);
                 call.enqueue(new Callback<ResponseFormat>() {
                    @Override
                    public void onResponse(Response<ResponseFormat> response, Retrofit retrofit) {
                        Log.e("result", response.body().getData().toString());

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("Error", t.getMessage());
                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void css() {
        TextView tvToLogin = findViewById(R.id.tvToLogin);
        tvToLogin.setTextColor(0xAA64bfef);
    }

    public void clickToLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    // 이메일 , 전화번호 정규식 따로 검사하기.
    // 디비에 회원가입여부 저장
    // 디비에 저장된 데이터를 비교하면서 중복확인
    // 디비에 저장된 데이터를 바탕으로 로그인 하기
    // 페이스북 로그인을 이용해 사용자 데이터 불러오기
    // 메인화면 구현하기

}
