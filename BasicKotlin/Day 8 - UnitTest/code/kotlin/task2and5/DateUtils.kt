package com.example.unittest.task2and5

open class DateUtils {
    fun getDatetimefromIso8583Format(input: String): String{
        return "20"+input
    }

    fun converYear(input: String): String {
        return "20"+input
    }

    fun isCurrentYear(): Boolean {
        if (getCurrentYear()==2022)
            return true
        else
            return false
    }

    fun getCurrentYear(): Int {
        return 2021
    }

}