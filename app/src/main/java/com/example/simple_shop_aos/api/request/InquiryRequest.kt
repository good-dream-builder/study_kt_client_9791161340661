package com.example.simple_shop_aos.api.request

data class InquiryRequest(
    val type: String,   // QUESTION, ANSWER
    val inquiryId: Long?,
    val productId: Long,
    val content: String?
) {
    val isContentEmpty = content.isNullOrEmpty()
}