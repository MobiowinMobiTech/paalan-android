package com.phyder.paalan.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.HeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jay on 02/07/16.
 *
 * @author Jayant Tiwari
 */
public class NetworkUtil {

    private static Retrofit sRetrofit;
    private static Gson sGson;
    boolean connected = false;

    public static Gson getGson() {
        if (sGson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .setDateFormat("yyyy-mm-DD");
            sGson = gsonBuilder.create();
        }
        return sGson;
    }

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {

            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.connectTimeout(60, TimeUnit.SECONDS);

            //   Default Header Interceptor
            HeaderInterceptor heaederInterceptor = new HeaderInterceptor();
            heaederInterceptor.setUserAgent(Device.getInstance().getUserAgent());
            clientBuilder.addInterceptor(heaederInterceptor);

//            if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);
//
//            }

//            Log.i("SERVER", "" + BuildConfig.DEBUG);
//            http://192.168.0.15:8080/paalan/PaalanGateway
            sRetrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.15:8080/")
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .client(clientBuilder.build())
                    .build();
        }
        return sRetrofit;
    }
}
