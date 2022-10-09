package com.example.kotlinbasics.day3

class Robot1() {
    //coordinates x,y
    var gridPosition: GridPosition = GridPosition(0,0)
        get() = field
    //direction
    var direction: Direction = Direction.NORTH
        get() = field
    //constructor
    constructor(gridPosition: GridPosition,direction: Direction) : this() {
        this.gridPosition=gridPosition
        this.direction=direction
    }

    fun turnLeft() {
        direction = direction.turnLeft()
    }
    fun turnRight() {
        direction = direction.turnRight()
    }
    fun advance() {
        gridPosition = gridPosition.advance(direction)
    }
    //control robot with string commands
    fun control(commands: String) {
        commands.forEach { command ->
            when(command) {
                'R' -> turnRight()
                'L' -> turnLeft()
                'A' -> advance()
            }
        }
    }
}
