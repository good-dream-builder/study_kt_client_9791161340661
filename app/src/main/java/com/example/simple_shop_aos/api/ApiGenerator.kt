package com.example.simple_shop_aos.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiGenerator {

    fun <T> generate(api: Class<T>): T =
        Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient())
            .build()
            .create(api)

    fun <T> generateRefreshClient(api:Class<T>): T =
        Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(refreshClient())
            .build()
            .create(api)

    private fun httpClient() =
        OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor())
            addInterceptor(ApiTokenInterceptor())
            authenticator(TokenAuthenticator())
        }.build()

    private fun refreshClient() =
        OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor())
            addInterceptor(TokenRefreshInterceptor())
        }.build()

    private fun httpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    companion object {
        const val HOST = "http://192.168.0.4:8080"
    }
}