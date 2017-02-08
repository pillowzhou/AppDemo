package com.android.apptest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * <p>
 * <p>
 * "@interface"
 * 是用于自定义注解的，它里面定义的方法的声明不能有参数
 * 也不能抛出异常，并且方法的返回值被限制为简单类型、String、Class、emnus、@interface，和这些类型的数组
 * <p>
 * 注解@Target也是用来修饰注解的元注解，它有一个属性ElementType也是枚举类型，值为：ANNOTATION_TYPE，CONSTRUCTOR ，FIELD，LOCAL_VARIABLE，METHOD，PACKAGE，PARAMETER和TYPE，如@Target(ElementType.METHOD) 修饰的注解表示该注解只能用来修饰在方法上。
 * <p>
 * "@RetentionRetention"注解表示需要在什么级别保存该注释信息，用于描述注解的生命周期，它有一个RetentionPolicy类型的value，是一个枚举类型，它有以下的几个值：
 * <p>
 * 1.用@Retention(RetentionPolicy.SOURCE)修饰的注解，指定注解只保留在源文件当中，编译成类文件后就把注解去掉；
 * 2.用@Retention(RetentionPolicy.CLASS)修饰的注解，指定注解只保留在源文件和编译后的class 文件中，当jvm加载类时就把注解去掉；
 * 3.用@Retention(RetentionPolicy.RUNTIME )修饰的注解，指定注解可以保留在jvm中，这样就可以
 *
 *
 * Created by zhoujian on 2017/2/8.
 */

@Target(ElementType.FIELD) // 表示用在字段上
@Retention(RetentionPolicy.RUNTIME) // 表示其生命周期是在运行时
public @interface ViewBinder {

    int id() default -1;

    String method() default "";

    String type() default "";
}
