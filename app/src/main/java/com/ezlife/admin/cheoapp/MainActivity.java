package com.ezlife.admin.cheoapp;

import android.content.DialogInterface;
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

        EzAdDialog dialog = new EzAdDialog(this, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
        dialog.showDialog();

    }
}
