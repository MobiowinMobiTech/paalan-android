package com.mobiowin.paalan.services;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jay on 12/07/16.
 *
 * @author Uncle
 */
public class HeaderInterceptor implements Interceptor {

    private String mUserAgent;

    public HeaderInterceptor() {
    }

    public HeaderInterceptor(String userAgent) {
        this.mUserAgent = userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.mUserAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request.Builder builder = originalRequest.newBuilder();

        if (originalRequest.header("module") == null) {
            builder.header("module", "mobile");
        }

        return chain.proceed(builder.build());
    }
}
