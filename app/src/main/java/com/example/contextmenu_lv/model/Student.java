package com.example.contextmenu_lv.model;

public class Student {
    private String mName;
    private String mClass;

    public Student(String mName, String mClass) {
        this.mName = mName;
        this.mClass = mClass;
    }

    public String getmName() {
        return mName;
    }

    public String getmClass() {
        return mClass;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmClass(String mClass) {
        this.mClass = mClass;
    }
}
