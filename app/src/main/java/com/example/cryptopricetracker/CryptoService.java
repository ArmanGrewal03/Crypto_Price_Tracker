package com.example.cryptopricetracker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoService {

    @GET("v1/cryptocurrency/listings/latest")
    Call<ApiResponse> getCryptoData();
}
