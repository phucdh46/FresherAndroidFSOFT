package com.example.kotlinbasics.day4.task3

//generic method to find the maximal element in the range [begin, end) of a list
class MaximalElement<T: Comparable<T>> (var array: List<T> ,var begin: Int,var end: Int  ) {
    fun findMax (): T {
        var max: T = array.get(begin)
        for (i in begin++..end--) {
            var term: T = array.get(i)
            if (term.compareTo(max) > 0) {
                max = term
            }
        }
        return max
    }
}

fun main () {
    //find the maximal element of a list integer in range 1-3
    var arrayInt: List<Int> = listOf(1,2,4,6,7,3,13)
    var maximalInt = MaximalElement(arrayInt,1,3)
    val maxInt = maximalInt.findMax()
    println(maxInt)

    //find the maximal element of a list double in range 1-4
    var arrayDouble: List<Double> = listOf(1.3,2.6,1.5,5.8,0.5,9.1)
    var maximalDouble = MaximalElement(arrayDouble,1,4)
    val maxDouble = maximalDouble.findMax()
    println(maxDouble)
}

