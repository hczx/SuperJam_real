package com.hc.android.superjam.data.sql;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.imageutils.BitmapUtil;
import com.hc.android.superjam.R;

/**
 * Created by 99165 on 2016/4/11.
 */
public class SQLiteActivity extends AppCompatActivity {

    private MySQLiteHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        mHelper = new MySQLiteHelper(this, MySQLiteHelper.DATABASE_NAME, null, 1);
        //创建数据库
        Button createDatabase = (Button) this.findViewById(R.id.createBtn);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mHelper.getWritableDatabase();
            }
        });

        //更新数据库
        Button updateDatabase = (Button) this.findViewById(R.id.updateBtn);
        updateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        Button addData = (Button) this.findViewById(R.id.addBtn);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mHelper.getWritableDatabase();
                //方法一
                ContentValues values = new ContentValues();
                values.put("author", "Jam He");
                values.put("price", 99.99);
                values.put("pages", 850);
                values.put("name", "机器码");
                db.insert(MySQLiteHelper.TABLE_BOOK,null,values);
                values.clear();
                values.put("author", "Jam Li");
                values.put("price", 5.30);
                values.put("pages", 120);
                values.put("name", "就付款时间");
                db.insert(MySQLiteHelper.TABLE_BOOK,null,values);

                //方法二
                db.execSQL("");
            }
        });
    }
}
