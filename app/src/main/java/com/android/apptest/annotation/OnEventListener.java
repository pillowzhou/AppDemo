package com.android.apptest.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Method;

/**
 * Created by zhoujian on 2017/2/8.
 */

public class OnEventListener {

    private Object object;

    public OnEventListener(Object object) {
        this.object = object;
    }

    public void setOnclick(int id, final String methodName) {
        View view = null;
        if(object instanceof View) {
            view = ((View) object).findViewById(id);
        } else if (object instanceof Activity) {
            view = ((Activity) object).findViewById(id);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class clazz = object.getClass();
                try {
                    Method method = clazz.getMethod(methodName, new Class[]{});
                    method.invoke(object, new Object[]{});
                } catch (Exception e) {

                }

            }
        });












    }
}
