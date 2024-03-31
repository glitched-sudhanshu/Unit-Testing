package com.example.unittesting.mvvm

import com.example.unittesting.FileReaderUtil
import com.example.unittesting.mvvm.api.ProductsAPI
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsAPITest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var productsAPI: ProductsAPI

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        productsAPI =
            Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ProductsAPI::class.java)
    }

    @Test
    fun testGetProducts_expected_emptyList() =
        runTest {
            val mockResponse = MockResponse()
            mockResponse.setBody("[]")
            mockWebServer.enqueue(mockResponse)
            val response = productsAPI.getProducts()
            mockWebServer.takeRequest()
            assertEquals(true, response.body()!!.isEmpty())
        }

    @Test
    fun testGetProducts_expected_productsList() =
        runTest {
            val mockResponse = MockResponse()
            val content = FileReaderUtil.execute("/products_test_response.json")
            mockResponse.setResponseCode(200)
            mockResponse.setBody(content)
            mockWebServer.enqueue(mockResponse)
            val response = productsAPI.getProducts()
            mockWebServer.takeRequest()
            assertEquals(false, response.body()!!.isEmpty())
            assertEquals(200, response.code())
            assertEquals(2, response.body()!!.size)
        }

    @Test
    fun testGetProducts_expected_error() =
        runTest {
            val mockResponse = MockResponse()
            val content = FileReaderUtil.execute("/products_test_response.json")
            mockResponse.setResponseCode(400)
            // kuch bhi daal skte ho body, fail toh karegi hi
            mockResponse.setBody(content)
            mockResponse.setBody("something went wrong!")
            mockWebServer.enqueue(mockResponse)
            val response = productsAPI.getProducts()
            mockWebServer.takeRequest()
            assertEquals(false, response.isSuccessful)
            assertEquals(400, response.code())
        }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
