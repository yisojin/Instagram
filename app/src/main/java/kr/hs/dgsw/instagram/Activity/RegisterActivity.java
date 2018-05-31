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
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import kr.hs.dgsw.instagram.Common.IsValid;
import kr.hs.dgsw.instagram.Database.DBManager;
import kr.hs.dgsw.instagram.R;

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

        css();

        dbManager = new DBManager(RegisterActivity.this, "instagram.db", null, 1);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String tel = etTel.getText().toString();
                final String name = etName.getText().toString();
                final String account = etAccount.getText().toString();
                final String password = etPassword.getText().toString();

                if (isValid.isPhone(tel)) {
                    Log.i("tel", tel);
                    dbManager.insert("INSERT INTO user(tel, name, account, password) VALUES (\'" + tel + "\',\'" + name + "\',\'" + account + "\',\'" + password + "\');");

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Tel is fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * facebook sign in (sign up)
         */
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile", "email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result", object.toString());

                    }
                });

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Error", error.toString());
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
