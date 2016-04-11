package com.hc.android.superjam;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hc.android.superjam.commonadapter.CommonRecyclerAdapter;
import com.hc.android.superjam.commonadapter.RecyclerViewHolder;
import com.hc.android.superjam.data.file.SaveDataToFileActivity;
import com.hc.android.superjam.data.sql.SQLiteActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.simple_recyclerView)
    RecyclerView simpleRecyclerView;
    private List<FuncationModel> mData = new ArrayList<>();


    public static void startMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        simpleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleRecyclerView.setAdapter(new CommonRecyclerAdapter<FuncationModel>(mData, this, R.layout.simple_item_button) {
            @Override
            public void convert(RecyclerViewHolder holder, final FuncationModel funcationModel, int position) {
                holder.setText(R.id.simple_button, funcationModel.getFuncation_name())
                        .setOnItemClickListener(new RecyclerViewHolder.OnItemCliceListener() {
                            @Override
                            public void itemClick(View itemView, int position) {
                                Intent intent = new Intent(MainActivity.this, funcationModel.getaClass());
                                ActivityCompat.startActivity(MainActivity.this, intent, null);
                            }
                        });
            }
        });
    }

    private void initData() {
        mData.add(createFuncationModel(SaveDataToFileActivity.class, "保存数据到文件"));
        mData.add(createFuncationModel(SQLiteActivity.class, "数据库"));
    }


    public FuncationModel createFuncationModel(Class aClass, String n) {
        FuncationModel model = new FuncationModel();
        model.setaClass(aClass);
        model.setFuncation_name(n);
        return model;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
