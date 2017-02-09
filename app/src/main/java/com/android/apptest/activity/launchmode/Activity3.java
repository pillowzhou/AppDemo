package com.android.apptest.activity.launchmode;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.android.apptest.R;

/**
 * Created by zhoujian on 2017/2/9.
 */

public class Activity3 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.launch_mode_activity1);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).addView(DailView.getFloatView(Activity3.this), DailView.getFloatView(Activity3.this).getWindowManagerParams());
            }
        });
    }
}
