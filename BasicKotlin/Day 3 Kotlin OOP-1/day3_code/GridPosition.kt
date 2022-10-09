package com.example.kotlinbasics.day3

//coordinates x,y of robot
data class GridPosition(var x: Int,var y: Int) {
    //advance robot
    fun advance(o: Direction) = when(o) {
        Direction.NORTH -> GridPosition(x, y + 1)
        Direction.WEST -> GridPosition(x - 1, y)
        Direction.EAST -> GridPosition(x + 1, y)
        Direction.SOUTH -> GridPosition(x, y - 1)
    }
}
