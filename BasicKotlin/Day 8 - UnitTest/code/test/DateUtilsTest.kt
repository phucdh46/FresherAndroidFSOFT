package com.example.unittest

import com.example.unittest.task2and5.DateUtils
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class DateUtilsTest {
    @Test
    fun test_DateUtils() {
        val input = "0419"
        val dateUtils = DateUtils()
        val result = dateUtils.getDatetimefromIso8583Format(input)
        Assert.assertEquals("200419", result)
    }

    @Test
    fun test_verify_converYear() {
        val mock = Mockito.mock(DateUtils::class.java)
        val input = "0419"
        Mockito.verify(mock).converYear(input)
    }

    @Test
    fun test_isCurrentYear() {
        val dateUtils = DateUtils()
        Assert.assertEquals(2021, dateUtils.getCurrentYear())
        Assert.assertEquals(false, dateUtils.isCurrentYear())
    }
}