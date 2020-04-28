package com.example.simple_shop_aos.api

import com.example.simple_shop_aos.api.request.SignupRequest
import com.example.simple_shop_aos.api.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceApi {

    @POST("/api/v1/users")
    suspend fun signup(@Body signupRequest: SignupRequest): ApiResponse<Void>

    @GET("/api/v1/hello")
    suspend fun hello(): ApiResponse<String>

    companion object {
        val instance = ApiGenerator().generate(ServiceApi::class.java)
    }
}