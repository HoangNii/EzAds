package com.ezlife.admin.cheoapp;

import android.app.Activity;
import android.os.Bundle;

import cheoapp.admin.ezlife.com.ezad.EzAdDialog;

public class MainActivity extends Activity {

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
            public void onAction(Action action, String apId) {
                if(action!=Action.SHOW)finish();
            }

        }.showDialog();

    }
}
