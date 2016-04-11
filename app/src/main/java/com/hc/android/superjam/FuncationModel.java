package com.hc.android.superjam;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 99165 on 2016/4/11.
 */
public class FuncationModel {
    private String funcation_name;
    private Class aClass;

    public String getFuncation_name() {
        return funcation_name;
    }

    public void setFuncation_name(String funcation_name) {
        this.funcation_name = funcation_name;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
