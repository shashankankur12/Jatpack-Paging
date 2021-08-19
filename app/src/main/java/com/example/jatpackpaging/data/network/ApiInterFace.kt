package com.example.jatpackpaging.data.network

import com.example.jatpackpaging.data.responses.FoodList
import com.example.jatpackpaging.utils.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterFace {

    @GET("search")
    suspend fun getAllFoodData(
        @Header("Authorization") auth: String,
        @Query("page") page: String,
        @Query("query") query: String
    ): Response<FoodList>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): ApiInterFace {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val okHttpClient =
                OkHttpClient.Builder()
                    .addInterceptor(interceptor).build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://food2fork.ca/api/recipe/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterFace::class.java)
        }
    }
}