package kr.hs.dgsw.instagram.Activity;

import android.content.Context;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.hs.dgsw.instagram.R;

public class RegisterActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        css();

        final EditText etEmailOrTel = (EditText) findViewById(R.id.etEmailOrTel);
        EditText etName = (EditText) findViewById(R.id.etName);
        EditText etAccount = (EditText) findViewById(R.id.etAccount);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final String emailOrTel = etEmailOrTel.getText().toString();
        String name = etName.getText().toString();
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (isValid(emailOrTel)) {
                        Log.i("email or tel",emailOrTel);
                    } else {
                        Toast.makeText(RegisterActivity.this, "email is fail", Toast.LENGTH_SHORT).show();
                    }




            }
        });

        /**
         *  facebook login
         */

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        // hash key console 에 띄우기
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "kr.hs.dgsw.instagram",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }

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

    public static boolean isValid(String emailOrTel){

        boolean returnValue = false;

        String regex="^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$" ;

        if(emailOrTel.contains("@")){
            Log.i("email", emailOrTel);
            regex = " ^[a-zA-Z0-9]+@[a-zA-Z0-9]+$";
        }

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(emailOrTel);

        if (m.matches()) {

            returnValue = true;

        }

        return returnValue;
    }

    public static boolean isValidCellPhoneNumber(String cellphoneNumber) {

        boolean returnValue = false;

        Log.i("cell", cellphoneNumber);

        String regex = "^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$";

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(cellphoneNumber);

        if (m.matches()) {

            returnValue = true;

        }

        return returnValue;

    }

    public static boolean isValidEmail(String email) {
        boolean returnValue = false;

        Log.i("email", email);


        String regex = " ^[a-zA-Z0-9]+@[a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);

        if (m.matches()) {
            returnValue = true;
        }
        return returnValue;

    }

}
