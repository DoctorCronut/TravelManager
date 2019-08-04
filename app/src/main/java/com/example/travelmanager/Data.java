package com.example.travelmanager;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Data {

    private String mName;
    private float mExpense;
    private String mDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Data(String name, float expense){
        LocalDate curDate = java.time.LocalDate.now();
        mDate = curDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        mName = name;
        mExpense = expense;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Data(String name, float expense, String date){
        mDate = date;
        mName = name;
        mExpense = expense;
    }

    public String getmName() {
        return mName;
    }

    public float getmExpense() {
        return mExpense;
    }

    public String getmDate() {
        return mDate;
    }
}
