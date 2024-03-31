@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.unittesting.mvvm.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.unittesting.coroutines.MainCoroutineRule
import com.example.unittesting.getOrAwaitValue
import com.example.unittesting.mvvm.models.ProductListItem
import com.example.unittesting.mvvm.repository.ProductRepository
import com.example.unittesting.utils.NetworkResult
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class StoreViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var sut: StoreViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = StoreViewModel(productRepository)
    }

    @Test
    fun test_getEmptyProductsList() {
        runTest {
            Mockito.`when`(productRepository.getProducts()).thenReturn(
                NetworkResult.Success(
                    emptyList(),
                ),
            )
            sut.getProducts()
            mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
            val result = sut.products.getOrAwaitValue()
            assertEquals(0, result.data?.size)
        }
    }

    @Test
    fun test_getErrorInProductsList() =
        runTest {
            Mockito.`when`(productRepository.getProducts()).thenReturn(
                NetworkResult.Error(
                    "Something went wrong",
                ),
            )
            sut.getProducts()
            mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
            val result = sut.products.getOrAwaitValue()
            assertEquals(true, result is NetworkResult.Error)
            assertEquals("Something went wrong", result.message)
        }

    @Test
    fun test_getProductsList_expected_size_2() =
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

            Mockito.`when`(productRepository.getProducts()).thenReturn(NetworkResult.Success(expectedResult))
            sut.getProducts()
            mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
            val result = sut.products.getOrAwaitValue()
            assertEquals(expectedResult, result.data)
        }

    @After
    fun tearDown() {
    }
}
