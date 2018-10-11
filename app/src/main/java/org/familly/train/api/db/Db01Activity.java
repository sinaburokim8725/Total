package org.familly.train.api.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.familly.train.api.R;

public class Db01Activity extends AppCompatActivity {
    SQLiteDatabase db;
    TextView text_resultInfo;

    EditText edit_dbName;
    Button button_openDB;

    EditText edit_tableName;
    Button button_createTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db01);
        //뷰객체 참조
        edit_dbName =(EditText) findViewById(R.id.edit_dbName);
        button_openDB = (Button) findViewById(R.id.button_openDB);

        edit_tableName = (EditText) findViewById(R.id.edit_tableName);
        button_createTable = (Button) findViewById(R.id.button_createTable);

        //
        text_resultInfo = (TextView) findViewById(R.id.text_resultInfo);

        //db 생성및 오픈 즉 커넥트 객체 획득
        button_openDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edittext에 쓰여진 문자열로 db를 생성
                String dbName = edit_dbName.getText().toString().trim();
                openDatabase(dbName);
            }
        });

        //테이블생성
        button_createTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String tableName = edit_tableName.getText().toString().trim();
                createTable(tableName);
            }
        });
    }
    public void createTable(String tableName){
        println("createTable() 호출됨 ");
        if(db != null){
            String sql = "CREATE TABLE " + tableName
                         + " (" + "_id INTEGER PRIMARY KEY autoincrement,"
                         +  "name TEXT,"
                         +  "age  INTEGER,"
                         +  "mobile TEXT" +
                      ");";
            //쿼리실행
            db.execSQL(sql);
            println("테이블 생성됨");

        } else {
            println("먼저데이터베이스를 오픈하세요.");
        }
    }
    public void openDatabase(String dbName){
        println("openDatabase 호출 ");
        //db생성및오픈
        db =  openOrCreateDatabase(dbName,MODE_PRIVATE,null);
        if(db.isOpen()){
            println("db 오픈됨");
        }
    }
    public void println(String info){
        text_resultInfo.append(info + "\n");
    }
}
