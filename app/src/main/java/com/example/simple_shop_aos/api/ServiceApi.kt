package com.example.simple_shop_aos.api

import com.example.simple_shop_aos.api.request.ProductRegistrationRequest
import com.example.simple_shop_aos.api.request.SigninRequest
import com.example.simple_shop_aos.api.request.SigninResponse
import com.example.simple_shop_aos.api.request.SignupRequest
import com.example.simple_shop_aos.api.response.ApiResponse
import com.example.simple_shop_aos.api.response.ProductImageUploadResponse
import com.example.simple_shop_aos.api.response.ProductListItemResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ServiceApi {
    @Multipart
    @POST("/api/v1/product_images")
    suspend fun uploadProductImages(
        @Part images: MultipartBody.Part
    ): ApiResponse<ProductImageUploadResponse>

    @POST("/api/v1/products")
    suspend fun registerProduct(
        @Body request: ProductRegistrationRequest
    ): ApiResponse<Response<Void>>

    @GET("/api/v1/products")
    suspend fun getProducts(
        @Query("productId") productId: Long,
        @Query("categoryId") category: Int?,
        @Query("direction") direction: String    // prev, next
    ): ApiResponse<List<ProductListItemResponse>>

    @POST("/api/v1/signin")
    suspend fun signin(@Body signinRequest: SigninRequest): ApiResponse<SigninResponse>

    @POST("/api/v1/users")
    suspend fun signup(@Body signupRequest: SignupRequest): ApiResponse<Void>

    @GET("/api/v1/hello")
    suspend fun hello(): ApiResponse<String>


    companion object {
        val instance = ApiGenerator().generate(ServiceApi::class.java)
    }
}