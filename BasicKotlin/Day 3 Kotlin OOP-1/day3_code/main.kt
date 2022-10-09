package com.example.kotlinbasics.day3

import java.util.*

val scan = Scanner(System.`in`)

fun main () {
    var robot = Robot1(GridPosition(7,3),Direction.NORTH)
    println("Current position: {${robot.gridPosition.x},${robot.gridPosition.y}} facing ${robot.direction}")
    robot.control(enterString())
    println("New position: {${robot.gridPosition.x},${robot.gridPosition.y}} facing ${robot.direction}")
}

//enter input string control robot
fun enterString(): String {
    var controlString: String
    do {
        print("Enter control characters with (A: advance, R: turn right, L: turn left): ")
        controlString = scan.nextLine()
    }while (!checkString(controlString))
    return controlString
}

//Check input string include char A,R,L
fun checkString(commands: String): Boolean {
    for (i in 0..commands.length-1){
        if (commands[i] != 'A' && commands[i] != 'L' && commands[i] != 'R' ){
            return false
        }
    }
    return true
}


