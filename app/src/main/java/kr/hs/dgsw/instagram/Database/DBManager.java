package kr.hs.dgsw.instagram.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by leesojin on 2018. 5. 16..
 */

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "emailOrTel TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "account TEXT NOT NULL" +
                ");"+"");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String qur){

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("CREATE TABLE IF NOT EXISTS user("+ "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "emailOrTel TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "account TEXT NOT NULL" +
                ");" +"");

        db.execSQL(qur);
        Log.d("query",qur);
        db.close();
    }

}
