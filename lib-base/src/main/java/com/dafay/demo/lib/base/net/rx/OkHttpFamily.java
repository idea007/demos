package com.dafay.demo.lib.base.net.rx;


import android.util.Log;

import com.example.demo.lib.net.HttpConfigManager;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * @description:
 * @Author: lipengfei
 * @Date: 2023/3/7
 * @Last Modified by: lipengfei
 * @Last Modified time: 2023/3/7
 */
public class OkHttpFamily {
    private static final String TAG = OkHttpFamily.class.getSimpleName();
    private static volatile OkHttpClient API;

    private OkHttpFamily() {
    }

    public static OkHttpClient API() {
        if (null == API) {
            synchronized (TAG) {
                if (null == API) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    // 云边请求调用鉴权
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(15L, TimeUnit.SECONDS)
                            .writeTimeout(15L, TimeUnit.SECONDS)
                            .readTimeout(15L, TimeUnit.SECONDS);

                    builder.addInterceptor(logging);
                    for (Interceptor interceptor : HttpConfigManager.INSTANCE.getConfig().getInterceptors()) {
                        builder.addInterceptor(interceptor);
                        Log.w(TAG, "addInterceptor" + interceptor.getClass().getSimpleName());
                    }
                    API = builder.build();
                }
            }
        }
        return API;
    }


}
