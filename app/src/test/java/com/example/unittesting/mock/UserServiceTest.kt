package com.example.unittesting.mock

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

// mockito is a java framework, and in kotlin by default all classes are final. But mockito cannot work on final classes.
class UserServiceTest {
    @Mock
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        // this line will initialize all the variables marked with @Mock
        MockitoAnnotations.openMocks(this)
        // I could have created our own object normally, but the main need of mocking is that we describe the required behaviour and based on that we write the testcases, which in turns helps us to remove dependency on other classes.
        Mockito.`when`(userRepository.loginUser(anyString(), anyString()))
            .thenReturn(LoginStatus.INVALID_USER)
    }

    @Test
    fun testUserService_userDoesNotExist() {
        val sut = UserService(userRepository)
        val status = sut.loginUser("sc011@gmail.com", "11111")
        assertEquals("User does not exist", status)
    }

    @Test
    fun testUserService_userSuccessfullyLoggedIn() {
        Mockito.`when`(userRepository.loginUser(anyString(), anyString()))
            .thenReturn(LoginStatus.SUCCESS)
        val sut = UserService(userRepository)
        val status = sut.loginUser("sc0119@gmail.com", "11111")
        assertEquals("Logged in successfully", status)
    }
}
