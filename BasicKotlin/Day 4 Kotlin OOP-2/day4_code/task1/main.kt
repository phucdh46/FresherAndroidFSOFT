package com.example.kotlinbasics.day4.task1

//find odd number
class OddNumber : UnaryPredicate<Int?> {
    override fun checkNumber(obj: Int?): Boolean {
        if (obj != null) {
            return obj % 2 != 0
        }
        else{
            return false
        }
    }
}
//find prime numbers
class PrimeNumbers : UnaryPredicate<Int?> {
    override fun checkNumber(obj: Int?): Boolean {
        if (obj != null) {
            for (i in 2..obj/2){
                if (obj % i == 0){
                    return false
                    break
                }
            }
            return true
        }
        else{
            return false
        }
    }
}
//find palindromes number
class Palindromes : UnaryPredicate<String?> {
    override fun checkNumber(obj: String?): Boolean {
        if (obj != null) {
            var begin: Int = 0
            var end: Int = obj.length-1
            while (end.compareTo(begin)>0) {
                if (obj.get(begin) != obj.get(end)){
                    return false
                    break
                }
                else{
                    begin++
                    end--
                }
            }
            return true
        }
        else{
            return false
        }
    }
}

fun main() {
    //count number of odd integers
    val list: Collection<Int> = listOf(2,3,4,5,6,7,8,9,10)
    val oddNumber = Algorithm(list, OddNumber())
    val countOddNumber = oddNumber.countIf()
    println("Number of odd integers = $countOddNumber")

    //count number of prime numbers
    val list2: Collection<Int> = listOf(2,3,4,5,6,7,11,12,13)
    val primeNumbers = Algorithm(list2, PrimeNumbers())
    val countPrimeNumbers = primeNumbers.countIf()
    println("Number of prime numbers = $countPrimeNumbers")

    //count number of palindromes numbers
    val list3: Collection<String> = listOf("221122","12345","2342","234532","12345654321")
    val palindromes = Algorithm(list3, Palindromes())
    val countPalindromes = palindromes.countIf()
    println("Number of palindromes numbers = $countPalindromes")
}
