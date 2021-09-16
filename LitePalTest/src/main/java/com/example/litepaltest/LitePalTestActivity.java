package com.example.litepaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.litepaltest.bean.Book;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class LitePalTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal_test);

        //创建表
        Button btn_create_database = findViewById(R.id.btn_create_database);
        btn_create_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connector.getDatabase();
            }
        });

        //增
        Button btn_add_data = findViewById(R.id.btn_add_data);
        btn_add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("yuan");
                book.setAuthor("yuan");
                book.setPages(1000);
                book.setPress("unknown");
                book.save();
            }
        });

        //改
        Button btn_upate_data = findViewById(R.id.btn_upate_data);
        btn_upate_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setPages(1000);
                book.updateAll("name = ? and author =?","yuan","yuan");
                /*//new一个对象，所有字段已被初始化成默认值 -- book.setPages(0);不可以
                book.setToDefault("pages");
                book.updateAll();*/
            }
        });

        //删
        Button btn_delete_data = findViewById(R.id.btn_delete_data);
        btn_delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Book.class,"pages < ?","100");
            }
        });

        //查
        Button btn_query_data = findViewById(R.id.btn_query_data);
        btn_query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books = DataSupport.findAll(Book.class);
                Book firstBook = DataSupport.findFirst(Book.class);
                Book lastBook = DataSupport.findLast(Book.class);
                List<Book> books1 = DataSupport.select("name","author").find(Book.class);
                List<Book> books2 = DataSupport.where("pages > ?","400").find(Book.class);
                List<Book> books3 = DataSupport.order("pages desc").find(Book.class);
                //只查前三行数据
                List<Book> books4 = DataSupport.limit(3).find(Book.class);
                //查第二行、第三行、第四行数据
                List<Book> books5 = DataSupport.limit(3).offset(1).find(Book.class);
                //查11-20条页数大于400的"name","author","pages"，页数升序
                List<Book> books6 = DataSupport.select("name","author","pages")
                                                .where("pages > ?","400")
                                                .order("pages")
                                                .limit(10)
                                                .offset(10)
                                                .find(Book.class);
                /*//原生SQL(findBySQL返回cursor对象)
                Cursor cursor = DataSupport.findBySQL("select * from Book where pages > ? ","400");*/
            }
        });
    }
}