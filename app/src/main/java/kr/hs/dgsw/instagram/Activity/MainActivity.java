package kr.hs.dgsw.instagram.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.hs.dgsw.instagram.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }
}
