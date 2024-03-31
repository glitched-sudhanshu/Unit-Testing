@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.unittesting.mvvm.repository

import com.example.unittesting.mvvm.api.ProductsAPI
import com.example.unittesting.mvvm.models.ProductListItem
import com.example.unittesting.utils.NetworkResult
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class ProductRepositoryTest {
    @Mock
    private lateinit var productsAPI: ProductsAPI
    private lateinit var sut: ProductRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = ProductRepository(productsAPI)
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
            Mockito.`when`(productsAPI.getProducts()).thenReturn(Response.error(404, "Not found".toResponseBody()))
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

    @After
    fun tearDown() {
    }
}
