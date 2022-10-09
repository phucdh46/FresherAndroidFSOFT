package com.example.unittest

import com.example.unittest.task3.LogUtils
import com.example.unittest.task4.MainActivity
import io.mockk.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class LogUtilsTest {
    @Test
    fun test_setDebugModeTrue() {
        val mock = mockk<LogUtils>()
        every { mock.setDebugMode(any()) } returns true
        val result = mock.setDebugMode(true)
        verify { mock.setDebugMode(true) }
        assertEquals(true,result)
    }

    @Test
    fun test_setDebugModeFalse() {
        val mock = mockk<LogUtils>()
        every { mock.setDebugMode(any()) } returns false
        val result = mock.setDebugMode(false)
        verify { mock.setDebugMode(false) }
        assertEquals(false,result)
    }
}