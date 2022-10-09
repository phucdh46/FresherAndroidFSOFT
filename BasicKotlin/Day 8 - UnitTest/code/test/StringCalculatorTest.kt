package com.example.unittest

import com.example.unittest.task1.StringCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class StringCalculatorTest {
    @Test
    fun test_plus1() {
        val stringCalculator = StringCalculator()
        val result = stringCalculator.plus(2,2)
        assertEquals(4, result)
    }

    @Test
    fun test_plus2() {
        val stringCalculator = StringCalculator()
        val result = stringCalculator.plus(4,2)
        assertEquals(6, result)
    }

    @Test
    fun test_minus() {
        val stringCalculator = StringCalculator()
        val result = stringCalculator.minus(4,2)
        assertEquals(2, result)
    }

    @Test
    fun test_divided() {
        val stringCalculator = StringCalculator()
        val result = stringCalculator.divided(6,2)
        assertEquals(3, result)
    }

    @Test
    fun test_mutil() {
        val stringCalculator = StringCalculator()
        val result = stringCalculator.mutil(2,3)
        assertEquals(6, result)
    }
}