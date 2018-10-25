package kr.hs.dgsw.instagram.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import kr.hs.dgsw.instagram.Fragment.ListFrag;
import kr.hs.dgsw.instagram.Fragment.LogFrag;
import kr.hs.dgsw.instagram.Fragment.PostFrag;
import kr.hs.dgsw.instagram.Fragment.SearchFrag;
import kr.hs.dgsw.instagram.Fragment.UserFrag;
import kr.hs.dgsw.instagram.R;

public class MainActivity extends AppCompatActivity {

    ImageButton btn1, btn2, btn3, btn4, btn5;
    FragmentManager fm;
    FragmentTransaction ft;
    ListFrag frag1;
    SearchFrag frag2;
    PostFrag frag3;
    LogFrag frag4;
    UserFrag frag5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Intent intent = new Intent(MainActivity.this, BoardListActivity.class);
        startActivity(intent);

        btn1 = findViewById(R.id.ibtnHome);
        btn2 = findViewById(R.id.ibtnSearch);
        btn3 = findViewById(R.id.ibtnAdd);
        btn4 = findViewById(R.id.ibtnLike);
        btn5 = findViewById(R.id.ibtnUser);

        frag1 = new ListFrag();
        frag2 = new SearchFrag();
        frag3 = new PostFrag();
        frag4 = new LogFrag();
        frag5 = new UserFrag();
        setFrag(0);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFrag(0);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(1);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(2);

                Intent intent = new Intent(MainActivity.this, BoardAddActivity.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(3);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(4);
            }
        });

        setFrag(0);
    }

    public void setFrag(int num) {
        fm = getFragmentManager();
        ft = fm.beginTransaction();

        switch (num) {
            case 0:
                ft.replace(R.id.frag_main, frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.frag_main, frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.frag_main, frag3);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.frag_main, frag4);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.frag_main, frag5);
                ft.commit();
                break;
        }

    }
}