package com.example.kotlinbasics.day4.task2

//generic method to exchange the positions of two different elements in an array
class ExchangePositions<T>(var a: Array<T>,var i: Int,var j: Int) {
    fun swap() {
        val temp = a[i]
        a[i] = a[j]
        a[j] = temp
    }
}

fun main () {
    //exchange the positions of two integer
    val arrayInt: Array<Int> = arrayOf(1,2,3,4)
    val exchangePositions = ExchangePositions(arrayInt,1,2)
    exchangePositions.swap()
    arrayInt.forEach { print("$it ") }
    println()

    //exchange the positions of two char
    val arrayChar: Array<String> = arrayOf("a","b","c","d")
    val exchangePositions1 = ExchangePositions(arrayChar,1,2)
    exchangePositions1.swap()
    arrayChar.forEach { print("$it ") }
}