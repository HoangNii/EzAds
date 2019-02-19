package com.ezlife.admin.cheoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cheoapp.admin.ezlife.com.ezad.EzAdDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EzAdDialog.loadJson(this);

    }

    @Override
    public void onBackPressed() {

        new EzAdDialog(this) {
            @Override
            public void onCancel() {
                finish();
            }
        }.showDialog();

    }
}
