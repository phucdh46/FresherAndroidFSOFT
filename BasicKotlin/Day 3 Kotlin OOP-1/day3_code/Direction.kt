package com.example.kotlinbasics.day3

//direction of robot
enum class Direction {
    NORTH, EAST, SOUTH, WEST;
    //turn left robot
    fun turnLeft() = when(this) {
        NORTH -> WEST
        WEST -> SOUTH
        SOUTH -> EAST
        EAST -> NORTH
    }
    //turn right robot
    fun turnRight() = when(this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}