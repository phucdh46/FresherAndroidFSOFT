package com.example.day8assignmentteamanagment.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tb_tea")
data class Tea(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val description: String,
    val origin: String,
    val ingredients: String,
    val sleepTime: Int,
    val cafieneLevel: Int,
) : Serializable