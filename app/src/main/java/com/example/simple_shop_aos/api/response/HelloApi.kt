package com.example.simple_shop_aos.api.response

import com.example.simple_shop_aos.api.ApiGenerator
import retrofit2.http.GET

interface HelloApi {

    @GET("/api/v1/hello")
    suspend fun hello(): ApiResponse<String>

    companion object {
        val instance = ApiGenerator().generate(HelloApi::class.java)
    }
}