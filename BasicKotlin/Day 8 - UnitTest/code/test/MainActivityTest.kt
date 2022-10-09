package com.example.unittest

import com.example.unittest.task4.MainActivity
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class MainActivityTest {
    @Test
    fun testMockMainActivity() {
        val mock = mockk<MainActivity>()
        //val spy = spyk<MainActivity>()
        every { mock.getData(any()) } returns "output"
        val result = mock.getData("Any")
        verify { mock.getData("Any") }
        assertEquals("output",result)

    }

    @Test
    fun testSpyMainActivity() {
        val mock = spyk<MainActivity>()
        every { mock.getData(any()) } returns "output"
        val result = mock.getData("Any")
        assertEquals("output",result)
    }
}