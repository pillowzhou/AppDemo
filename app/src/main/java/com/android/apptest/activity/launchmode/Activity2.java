package com.android.apptest.activity.launchmode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.apptest.R;

/**
 * Created by zhoujian on 2017/2/9.
 */

public class Activity2 extends AppCompatActivity {

    private WindowManager windowManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_mode_activity1);

        windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);


        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity2.this, Activity3.class);
                startActivity(intent);

            }
        });


        DailView floatView = DailView.getFloatView(this);
        floatView.setBackgroundResource(R.mipmap.ic_launcher);
        floatView.setPadding(0, getPixelFromDip(getResources().getDisplayMetrics(), 10), 0, getPixelFromDip(getResources().getDisplayMetrics(), 10));
//        floatView.setImageResource(R.drawable.call_icon_title_btn_phone);
        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).removeView(v);
                startActivity(new Intent(getApplicationContext(), Activity2.class));
            }
        });

        try {
            windowManager.removeViewImmediate(floatView);
        } catch (Exception e) {
            //e.printStackTrace();
            Log.e("debug", "error msg = " + e.getMessage());
        }
    }






    public int getPixelFromDip(DisplayMetrics dm, float dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm) + 0.5f);
    }
}
