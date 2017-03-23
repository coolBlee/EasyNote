package com.coolblee.coolblibrary.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void initVariables(Bundle savedInstanceState);

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void setViewsAction(Bundle savedInstanceState);

    protected abstract void loadData(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables(savedInstanceState);

        initViews(savedInstanceState);

        setViewsAction(savedInstanceState);

        loadData(savedInstanceState);
    }
}
