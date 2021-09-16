package com.example.contentprovidertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DatabaseProviderActivity extends AppCompatActivity {

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_provider);

        Button btn_add = findViewById(R.id.btn_add);
        Button btn_query = findViewById(R.id.btn_query);
        Button btn_delete = findViewById(R.id.btn_delete);
        Button btn_update = findViewById(R.id.btn_update);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.contentprovidertest/book");
                ContentValues values = new ContentValues();
                values.put("name","yuan");
                values.put("author","yuan");
                values.put("pages",1024);
                values.put("price",86.2);
                Uri newUri = getContentResolver().insert(uri,values);
                newId = newUri.getPathSegments().get(1);
            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.contentprovidertest/book");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                if (cursor != null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("DatabaseProvider", "name: "+name);
                        Log.d("DatabaseProvider", "author: "+author);
                        Log.d("DatabaseProvider", "pages: "+pages);
                        Log.d("DatabaseProvider", "price: "+price);
                    }
                    cursor.close();
                }
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.contentprovidertest/book");
                ContentValues values = new ContentValues();
                values.put("name","yuan");
                values.put("pages",1024);
                values.put("price",96.2);
                getContentResolver().update(uri,values,null,null);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.contentprovidertest/book");
                getContentResolver().delete(uri,null,null);
            }
        });
    }
}