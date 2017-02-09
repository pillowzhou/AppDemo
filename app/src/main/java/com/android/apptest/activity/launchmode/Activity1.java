package com.android.apptest.activity.launchmode;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.apptest.R;

/**
 *
 * 正常启动模式
 *
 * Created by zhoujian on 2017/2/9.
 */

public class Activity1 extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_mode_activity1);


        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.apptest", "com.android.apptest.activity.launchmode.Activity2"));
                startActivity(intent);

            }
        });
    }





}
