package com.example.kotlinday1

import java.util.*

val reader = Scanner(System.`in`)

fun task1 () {
    println("Numbers divisible by 7 but not multiples of 5, between 10 to 200:")
    for (x in 10..200) {
        if (x%7==0 && x%5!=0)
            print("$x, ")
    }
}

fun task2 () {
    print("Enter two-digit integer number:")
    var number1: Int = reader.nextInt()
    println("Convert to binary number: "+Integer.toBinaryString(number1))
    println("Convert to hexadecimal number: "+Integer.toHexString(number1))
}

fun task3 () {
    print("Enter Array size:")
    var n: Int = reader.nextInt()
    var array:IntArray = IntArray(n)
    println("Enter Array elements:")
    for (i in array.indices) {
        array[i] = reader.nextInt()
    }
    println("Sort the elements of an array in ascending order: " )
    array.sort()
    for (i in array) println(i)
}

fun task4 () {
    println("Enter a string: ")
    var string: String = reader.nextLine()
    val words = string.toString().trim()

    val totalWords = words.split("\\s".toRegex()).size
    println("Total number of words in string: "+totalWords)

    val sentences = words.split(". ").toMutableList()
    var output = ""
    for(sentence in sentences) {
        output += sentence.capitalize() +". "
    }
    output.trim()
    println("Capitalize the first letter of the word if it begins for a sentence:\n"+output)
}

fun task5 () {
    print("Enter month: ")
    var month = reader.nextInt()
    print("Enter year: ")
    var year = reader.nextInt()

    when(month) {
        4,6,9,11 -> println("30 day")
        2 -> {
            if (year%400==0 || (year%4==0 && year%100!=0))
                println("29 days")
            else
                println("28 days")
        }
        1,3,5,7,8,10,12 -> println("31 days")
        else -> {
            println("Please enter month number between (1-12)")
        }
    }
}

fun main () {
    //task1()
    //task2()
    //task3()
    //task4()
    task5()
}

