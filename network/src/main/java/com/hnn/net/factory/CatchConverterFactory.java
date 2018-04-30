package com.hnn.net.factory;

import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 网络缓存转换适配
 * Created by hnn on 2018/1/29.
 */

public class CatchConverterFactory extends Converter.Factory{

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        for (Annotation parameterAnnotation : parameterAnnotations) {
            Log.d("注解", parameterAnnotation.toString() + "=======");
        }
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
