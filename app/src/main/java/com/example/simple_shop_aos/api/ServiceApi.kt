package com.example.simple_shop_aos.api

import com.example.simple_shop_aos.api.request.*
import com.example.simple_shop_aos.api.response.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ServiceApi {

    /**
     * 로그인/로그아웃/토큰
     */
    @POST("/api/v1/signin")
    suspend fun signin(@Body signinRequest: SigninRequest): ApiResponse<SigninResponse>

    @POST("/api/v1/users")
    suspend fun signup(@Body signupRequest: SignupRequest): ApiResponse<Void>

    @PUT("/api/v1/users/fcm-token")
    suspend fun updateFcmToken(fcmToken: String): ApiResponse<Response<Void>>

    /**
     * 상품 관련
     */
    @GET("/api/v1/products")
    suspend fun getProducts(
        @Query("productId") productId: Long,
        @Query("categoryId") category: Int?,
        @Query("direction") direction: String,    // prev, next
        @Query("keyword") keyword: String? = null
    ): ApiResponse<List<ProductListItemResponse>>

    @GET("/api/v1/products/{id}")
    suspend fun getProduct(@Path("id") id: Long): ApiResponse<ProductResponse>

    @Multipart
    @POST("/api/v1/product_images")
    suspend fun uploadProductImages(
        @Part images: MultipartBody.Part
    ): ApiResponse<ProductImageUploadResponse>

    @POST("/api/v1/products")
    suspend fun registerProduct(
        @Body request: ProductRegistrationRequest
    ): ApiResponse<Response<Void>>

    /**
     * 상품 문의 관련
     */
    @GET("/api/v1/inquiries")
    suspend fun getInquiries(
        @Query("inquiryId") inquiryId: Long,
        @Query("productId") productId: Long? = null,
        @Query("requestUserId") requestUserId: Long? = null,
        @Query("productOwnerId") productOwnerId: Long? = null,
        @Query("direction") direction: String // prev, next
    ): ApiResponse<List<InquiryResponse>>

    @POST("/api/v1/inquiries")
    suspend fun registerInquiry(
        @Body request: InquiryRequest
    ): ApiResponse<Response<Void>>

    /**
     * 테스트
     */
    @GET("/api/v1/hello")
    suspend fun hello(): ApiResponse<String>

    companion object {
        val instance = ApiGenerator().generate(ServiceApi::class.java)
    }
}