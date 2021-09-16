package com.example.datapersistencetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.datapersistencetest.db.MyDatabaseHelper;

public class SQLiteTestActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    /**
     * dbHelper:
     * 1.Context
     * 2.数据库名
     * 3.查询数据的时候返回一个自定义的Cusor，一般是null
     * 4.当前数据库的版本号
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);

        //创建表
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);
        Button btn_create_database = findViewById(R.id.btn_create_database);
        btn_create_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        //增
        Button btn_add_data = findViewById(R.id.btn_add_data);
        btn_add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一条数据
                values.put("name","Book1");
                values.put("author","tom");
                values.put("pages",454);
                values.put("price",16.5);
                db.insert("Book",null,values);//插入第一条数据
                values.clear();
                //开始组装第一条数据
                values.put("name","Book2");
                values.put("author","dan");
                values.put("pages",554);
                values.put("price",22.5);
                db.insert("Book",null,values);//插入第一条数据

                //使用SQL操作数据库
                db.execSQL("insert into Book (name,author,pages,price) values(?,?,?,?)",
                        new String[]{"Book3","yuan","100","10.77"});
            }
        });

        //改
        Button btn_upate_data = findViewById(R.id.btn_upate_data);
        btn_upate_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",10.22);
                db.update("Book",values,"name = ?", new String[]{"Book2"});

                //使用SQL操作数据库
                db.execSQL("update Book set price = ? where name = ?",
                        new String[]{"20.22","Book3"});
            }
        });

        //删
        Button btn_delete_data = findViewById(R.id.btn_delete_data);
        btn_delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book","pages > ?",new String[]{"500"});

                //使用SQL操作数据库
                db.execSQL("delete from Book where pages > ?",
                        new String[] {"400"});
            }
        });

        //查
        Button btn_query_data = findViewById(R.id.btn_query_data);
        btn_query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do {
                       String name = cursor.getString(cursor.getColumnIndex("name"));
                       String author = cursor.getString(cursor.getColumnIndex("author"));
                       int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                       double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("TAG", "name: "+name);
                        Log.d("TAG", "author: "+author);
                        Log.d("TAG", "pages: "+pages);
                        Log.d("TAG", "price: "+price);
                    }while (cursor.moveToNext());
                }
                cursor.close();

                //使用SQL操作数据库
                db.rawQuery("select * from Book",null);
            }
        });
    }
}