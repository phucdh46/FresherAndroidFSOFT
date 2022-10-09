package com.example.kotlinbasics

import androidx.core.text.isDigitsOnly
import java.util.*
import kotlin.collections.ArrayList

val scanner = Scanner(System.`in`)

fun main() {

    //Task1
    val hexStr = 200.toHexString()
    println(hexStr)

    //Task2
    val result = "C8".toBinaryString()
    println(result)

    //Task3
    println("20 first numbers Fibonacci: ")
    fibonacci(20)

    //Task4
    println()
    val binaryNumber = "12345678".toBinaryString()
    println(Integer.parseInt(turnOffB2b3(binaryNumber).toString(),2).toHexString())


    //Task5
    //1.Input a primary account numbers (PAN)
    var panNumber: String = ""
    do {
        println("Enter PAN number: 12-19 digit and PAN only allow numberic characters")
        panNumber = scanner.nextLine()
        val sizeNumber: Int = panNumber.length
    }while (!checkNumber(panNumber))
    //2. Validate the entered PAN using Luhn algorithm.
    if (checkLuhn(panNumber)) {
        println("This is a valid card")
        checkCardType(panNumber)
    }
    else
        println("This is not a valid card")
    // 3. Print out to screen the card type of entered PAN.

}

//convert Int to hex
fun Int.toHexString(): String {
    return Integer.toHexString(this)
}

//convert hex to binary
fun String.toBinaryString(): String {
    val i = Integer.parseInt(this,16)
    return Integer.toBinaryString(i)
}

//Turn-off the bit 3 of byte 2 to 0
fun turnOffB2b3(binaryNumber: String): String{
    val chars = binaryNumber.toCharArray()
    chars[10] = '0'
    val result = String(chars)
    return result
}

//Fibonacci order by descending
fun fibonacci(n: Int) {
    var t1 = 0
    var t2 = 1
    val array = ArrayList<Int>()
    for (i in 1..n) {
        array.add(t1)
        val sum = t1 +t2
        t1 = t2
        t2 = sum
    }
    array.sortDescending()
    array.forEach { print("$it ") }
}

//check input PAN
fun checkNumber(s: String): Boolean {
    if (!isNumeric(s))
        return false
    else if (s.length >= 12 && s.length <=19)
        return true
    else
        return false
}

//check numeric characters
fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
}

//check PAN using Luhn algorithm
fun checkLuhn (PAN: String): Boolean{
    val size = PAN.length
    var sum:Int = 0
    var check:Boolean = false
    for (i in size-1 downTo 0){
        var temp: Int = PAN.get(i) - '0'
        if(check == true) temp = temp * 2
        sum += temp/10
        sum += temp%10
        check = !check
    }
    return (sum % 10 == 0)
}

//check card type of PAN
fun checkCardType(panNumber: String) {
    val n1 = (panNumber.get(0).toString().toInt())
    val n2 = (panNumber.subSequence(0,2).toString().toInt())
    val n4 = (panNumber.subSequence(0,4).toString().toInt())
    if(n1 == 4)
        println("VISA card")
    else if (n2 in 50..69 || n4 in 2221..2720)
        println("Master Card")
    else if (n4 in 3528..3589)
        println("JCB Card")
    else
        println("Unknow Card")
}