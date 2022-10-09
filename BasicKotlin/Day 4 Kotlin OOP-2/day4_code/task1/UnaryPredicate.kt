package com.example.kotlinbasics.day4.task1

interface UnaryPredicate<T> {
    fun checkNumber(obj: T): Boolean
}