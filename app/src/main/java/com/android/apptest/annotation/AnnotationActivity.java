package com.android.apptest.annotation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.apptest.R;

/**
 * Created by zhoujian on 2017/2/8.
 */

public class AnnotationActivity extends AppCompatActivity {

    @ViewBinder(id = R.id.test)
    protected TextView test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewBinderParser.inject(this);

        test.setText("hhhhhhhhh");
    }
}
