package org.familly.train.api.db;

import android.database.Cursor;
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

    Button   tInsert;
    EditText pName  ;
    EditText pAge   ;
    EditText pMobile;

    Button dataSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db01);
        //뷰객체 참조
        //db생성용
        edit_dbName =(EditText) findViewById(R.id.edit_dbName);
        button_openDB = (Button) findViewById(R.id.button_openDB);

        //Table 생성용
        edit_tableName = (EditText) findViewById(R.id.edit_tableName);
        button_createTable = (Button) findViewById(R.id.button_createTable);

        //데이타 생성 참조용
        tInsert = (Button) findViewById(R.id.button_dataInsert);
        pName   =(EditText) findViewById(R.id.edit_name);
        pAge    =(EditText) findViewById(R.id.edit_age);
        pMobile =(EditText) findViewById(R.id.edit_mobile);

        //4.데이터 조회참조
        dataSelect = (Button) findViewById(R.id.button_select);

        //로그기록용
        text_resultInfo = (TextView) findViewById(R.id.text_resultInfo);

        //1.db 생성및 오픈 즉 커넥트 객체 획득
        button_openDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edittext에 쓰여진 문자열로 db를 생성
                String dbName = edit_dbName.getText().toString().trim();
                openDatabase(dbName);
            }
        });

        //2.테이블생성
        button_createTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String tableName = edit_tableName.getText().toString().trim();
                createTable(tableName);
            }
        });

        //3.db table에 데이터 입력하기
        tInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = pName.getText().toString().trim();
                String mobile = pMobile.getText().toString().trim();
                String ageStr = pAge.getText().toString().trim();
                int age = -1; //Integer.parseInt(ageStr);
                try {

                    age = Integer.parseInt(ageStr);

                } catch (Exception ex) {
                }

                //
                insertData(name,mobile,age);
            }
        });

        //4.데이터 조회 하기
        dataSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //테이블의 이름을 인자로 넘기
                String tableName = edit_tableName.getText().toString().trim();
                selectData(tableName);
            }
        });

    }
    public void selectData(String tName) {
        //
        println("selectData() 호출됨.");

        if (db.isOpen()) {

            String sql = "SELECT NAME , AGE , MOBILE FROM " + tName;

            Cursor cursor = db.rawQuery(sql,null);
            println("조회된 데이터 총 건수 : " + cursor.getCount());

            for (int i = 0; i < cursor.getCount(); i++) {

                //커서가 처음에는 Index -1 requested 을 가리키고 있음으로 인덱스 0을
                //가리키게 한다.
                cursor.moveToNext();

                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);

                println("# " + i + "==>" + name + " , " + age + " , " + mobile);

            }
            cursor.close();

        } else {

            println("먼저데이터베이스를 오픈하세요.");

        }
    }
    public void insertData(String name,String mobile,int age) {
        println("insertData() 호출");
        if (db.isOpen()) {
            String sql = "INSERT INTO CUSTMER"
                    + " (name , mobile , age) VALUES (? ,? ,?);";

            Object[] params = {name , mobile , age};

            db.execSQL(sql,params);
            println("데이터 추가함.");
        } else {
            println("면저데이터베이스를 오픈하세요.");

        }

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

    /**
     *
     * @param dbName
     * 접속할 Database Name
     */
    public void openDatabase(String dbName){
        println("openDatabase 호출 ");
        //db생성및오픈
        db = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        if(db.isOpen()){
            println("db 오픈됨");
        }
    }
    public void println(String info){
        text_resultInfo.append(info + "\n");
    }
}
