package kr.hs.dgsw.instagram.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.hs.dgsw.instagram.Database.DBManager;
import kr.hs.dgsw.instagram.R;

public class RegisterActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DBManager dbManager;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        css();

        dbManager = new DBManager(RegisterActivity.this,"instagram.db",null,1);

        final EditText etEmailOrTel = (EditText) findViewById(R.id.etEmailOrTel);
        EditText etName = (EditText) findViewById(R.id.etName);
        EditText etAccount = (EditText) findViewById(R.id.etAccount);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final String emailOrTel = etEmailOrTel.getText().toString();
        final String name = etName.getText().toString();
        final String account = etAccount.getText().toString();
        final String password = etPassword.getText().toString();

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid(emailOrTel)) {
                    Log.i("email or tel", emailOrTel);
                    dbManager.insert("INSERT INTO user(emailOrTel, name, account, password) VALUES ("+emailOrTel+","+name+","+account+","+password+");");

                } else {
                    Toast.makeText(RegisterActivity.this, "email or tel is fail", Toast.LENGTH_SHORT).show();
                    etEmailOrTel.setText("");
                }
            }
        });

        /**
         *  facebook login
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

    public static boolean isValid(String emailOrTel) {

        boolean returnValue = false;

        //tel regex
        String regex = "^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$";

        if (emailOrTel.contains("@")) {
            Log.i("email", emailOrTel);
            //email regex
            regex = " ^[a-zA-Z0-9]+@[a-zA-Z0-9]+$";
        }

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(emailOrTel);

        if (m.matches()) {

            returnValue = true;

        }

        return returnValue;
    }

    // 이메일 , 전화번호 정규식 따로 검사하기.
    // 디비에 회원가입여부 저장
    // 디비에 저장된 데이터를 비교하면서 중복확인
    // 디비에 저장된 데이터를 바탕으로 로그인 하기
    // 페이스북 로그인을 이용해 사용자 데이터 불러오기
    // 메인화면 구현하기
    
}
