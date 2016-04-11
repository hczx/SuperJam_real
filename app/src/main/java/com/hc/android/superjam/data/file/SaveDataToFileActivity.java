package com.hc.android.superjam.data.file;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.text.TextUtils;
import android.widget.Button;

import com.hc.android.superjam.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 99165 on 2016/4/11.
 */
public class SaveDataToFileActivity extends AppCompatActivity {

    @Bind(R.id.textInputlayout)
    TextInputLayout mInputlayout;
    @Bind(R.id.save_confirm)
    ButtonBarLayout mConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_file);
        ButterKnife.bind(this);

        String inputText = load();
        if (!TextUtils.isEmpty(inputText)) {
            mInputlayout.getEditText().setText(inputText);
            mInputlayout.getEditText().setSelection(inputText.length());
            Snackbar.make(mInputlayout, "Restoring succeeded", Snackbar.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.save_confirm)
    public void onClick() {
        try {
            String s = mInputlayout.getEditText().getText().toString();
            save(s);
        } catch (NullPointerException e) {
            mInputlayout.setError("输入不能为空");
        }

    }

    /**
     * 保存
     *
     * @param s
     */
    private void save(String s) {
        FileOutputStream outputStream = null;
        BufferedWriter writer = null;
        try {
            outputStream = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Snackbar.make(mInputlayout, "保存成功", Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 读取
     *
     * @return
     */
    public String load() {
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            inputStream = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
