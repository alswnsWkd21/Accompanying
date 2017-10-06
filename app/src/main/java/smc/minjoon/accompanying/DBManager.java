package smc.minjoon.accompanying;

/**
 * Created by skaqn on 2017-10-06.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by skaqn on 2017-01-30.
 */

public class DBManager extends SQLiteOpenHelper {


    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);


    }

    @Override
    public void onCreate(SQLiteDatabase db) { //database 테이블과 테이블 속성 정해주는 함수 db를 열어 줄 필요없다. 매개변수를 db로 받기 때문에 즉 객체 생길 때 즉 앱실핼 될 때마다 한번 실행 된다.
        db.execSQL("CREATE TABLE NOTE (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, number TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // database 버전관리하는 함수
    }

    public void insert(String title, String content, String number) { // database 정보 추가 함수
        SQLiteDatabase db = getWritableDatabase(); // 데이터베이스의 쓰기 읽기 기능 부여
        db.execSQL("INSERT INTO NOTE VALUES(null, '" + title + "', '" + content + "', '" + number + "' );"); // 기능 부여를 해야 이 SQL문을 쓸 수 있다.
        db.close(); // 마지막에 database를 닫는다 아직 이유는 모르겠다. 물어보자
    }
    public void update(int _id, String title, String content, String number) {// database 정보 업데이트 하는 함수
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE NOTE SET title='" + title + "', content='" + content + "', '" + number + "'  WHERE _id=" + _id + ";");
        db.close();
    }
    public void delete(int _id) {// database 정보 업데이트 하는 함수
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM NOTE WHERE _id=" + _id + ";");
        db.close();
    }

    public  ArrayList<SingleItem> getResult() { // database에 담겨있는 정보를 Cusor를 이용해서 읽어서 가져와서 Arraylist items에 넣는 함수
        SQLiteDatabase db = getReadableDatabase();
        Cursor mCursor = null;
        ArrayList<SingleItem> items = new ArrayList<>();
        mCursor = db.query("NOTE", null, null, null, null, null, null);
        while (mCursor.moveToNext()) {

            SingleItem item = new SingleItem(
                    mCursor.getInt(mCursor.getColumnIndex("_id")),
                    mCursor.getString(mCursor.getColumnIndex("title")),
                    mCursor.getString(mCursor.getColumnIndex("content")),
                    mCursor.getString(mCursor.getColumnIndex("number"))
            );
            items.add(item);
        }
        mCursor.close();
        db.close();
        return items;
    }
}