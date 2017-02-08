package com.android.apptest.annotation;

import android.app.Activity;
import android.icu.text.DateFormat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by zhoujian on 2017/2/8.
 */

public class ViewBinderParser implements Parsable {

    private ViewBinderParser() {
    }

    public static void inject(Object o) {
        ViewBinderParser parser = new ViewBinderParser();
        try {
            parser.parser(o);
        } catch (Exception e) {
            Log.e("ViewBinderParser", "inject error; message = " + e.getMessage());
        }
    }

    @Override
    public void parser(Object object) throws Exception {

        View view = null;
        final Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 查看这个字段是否有我们自定义的注解类标志的
            if (field.isAnnotationPresent(ViewBinder.class)) {
                ViewBinder inject = field.getAnnotation(ViewBinder.class);
                int id = inject.id();
                if (id < 0) {
                    throw new Exception("id must not be null");
                }

                if (id > 0) {
                    field.setAccessible(true);
                    if (object instanceof View) {
                        view = ((View) object).findViewById(id);
                    } else if (object instanceof Activity) {
                        view = ((Activity) object).findViewById(id);
                    }
                    field.set(object, view); //给我们要找的字段设置值

                    String methodName = inject.method();
                    if (!TextUtils.isEmpty(methodName)) {
                        OnEventListener listener = new OnEventListener(object);
                        String type = inject.type();
                        if (TextUtils.isEmpty(type)) {
                            throw new Exception("please input the type of method");
                        }

                        if ("OnClick".equals(type)) {
                            listener.setOnclick(id, methodName);
                        }
                    }
                }
            }
        }


    }
}
