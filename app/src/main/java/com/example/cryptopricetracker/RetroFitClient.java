package com.example.cryptopricetracker;

import retrofit2.converter.gson.GsonConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

public class RetroFitClient {
    private static Retrofit retrofit;
    private static final String Link = "https://pro-api.coinmarketcap.com/";
    private static final String Key = "insert_key";

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request originalRequest = chain.request();
                        Request.Builder requestBuilder = originalRequest.newBuilder()
                                .header("X-CMC_PRO_API_KEY", Key);
                        Request newRequest = requestBuilder.build();
                        return chain.proceed(newRequest);
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Link)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
