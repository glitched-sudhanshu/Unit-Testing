@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.unittesting.mvvm.repository

import com.example.unittesting.mvvm.api.ProductsAPI
import com.example.unittesting.mvvm.models.ProductListItem
import com.example.unittesting.utils.NetworkResult
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRepositoryTest {
    @Mock
    private lateinit var productsAPI: ProductsAPI
    private lateinit var sut: ProductRepository

    // this is just to use mock web server to test repository. Repository can be tested in these two ways. Either we mock the API class or we mock the server
    private lateinit var mockWebServer: MockWebServer
    private lateinit var productsAPI2: ProductsAPI

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = ProductRepository(productsAPI)
        mockWebServer = MockWebServer()
        productsAPI2 =
            Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ProductsAPI::class.java)
    }

    @Test
    fun test_getProducts_fromApi_expected_emptyList() {
        runTest {
            Mockito.`when`(productsAPI.getProducts()).thenReturn(Response.success(emptyList()))
            val result = sut.getProducts()
            assertEquals(true, result is NetworkResult.Success)
            assertEquals(0, result.data?.size)
        }
    }

    @Test
    fun test_getProducts_fromApi_expected_error() {
        runTest {
            Mockito.`when`(productsAPI.getProducts())
                .thenReturn(Response.error(404, "Not found".toResponseBody()))
            val result = sut.getProducts()
            assertEquals(true, result is NetworkResult.Error)
            // coz we are using "Something went wrong" message in the repo class, it returns this text rather than "Not found"
//            assertEquals("Not found", result.message)
            assertEquals("Something went wrong", result.message)
        }
    }

    @Test
    fun test_getProducts_fromApi_expected_size_2() {
        runTest {
            val expectedResult =
                listOf(
                    ProductListItem(
                        category = "jewel",
                        description = "expensive jewel",
                        id = 121,
                        image = "https//",
                        price = 2000.0,
                        title = "wuhhooo",
                    ),
                    ProductListItem(
                        category = "clothings",
                        description = "jeans",
                        id = 201,
                        image = "https//::www",
                        price = 459.0,
                        title = "denim",
                    ),
                )
            Mockito.`when`(productsAPI.getProducts()).thenReturn(Response.success(expectedResult))
            val result = sut.getProducts()
            assertEquals(true, result is NetworkResult.Success)
            assertEquals(2, result.data?.size)
            assertEquals(expectedResult, result.data)
        }
    }

    @Test
    fun getProducts_expected_error_using_mockserver() =
        runTest {
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(400)
            mockResponse.setBody("something went wrong!")
            mockWebServer.enqueue(mockResponse)
            val response = productsAPI2.getProducts()
            mockWebServer.takeRequest()
            assertEquals(false, response.isSuccessful)
            assertEquals(400, response.code())
        }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
