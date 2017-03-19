package com.coolblee.coolblibrary.activity;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

    protected abstract void initVariables();

    protected abstract void initViews();

    protected abstract void loadData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();

        initViews();

        loadData();
    }
}
