package com.example.unittesting.mvvm.api

import com.example.unittesting.mvvm.models.ProductListItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductsAPI {
    @GET("/products")
    suspend fun getProducts(): Response<List<ProductListItem>>
}
