package com.example.simple_shop_aos.api

import com.example.simple_shop_aos.api.response.ApiResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface RefreshApi {

    @POST("/api/v1/refresh_token")
    suspend fun refreshToken(
        @Query("grant_type") grantType: String = "refresh_token"
    ): ApiResponse<String>

    companion object {
        val instance = ApiGenerator()
            .generateRefreshClient(RefreshApi::class.java)
    }
}