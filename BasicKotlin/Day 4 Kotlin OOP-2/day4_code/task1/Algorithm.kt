package com.example.kotlinbasics.day4.task1

//generic method to count the number of elements in a collection
class Algorithm<T>(var collection: Collection<T>,var  p: UnaryPredicate<T>) {
    fun countIf(): Int {
        var count = 0
        for (i in collection) if (p.checkNumber(i)){
            ++count
        }
        return count
    }
}