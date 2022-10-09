package com.example.assignmentday5

import java.io.Serializable

data class Card(
    val name: String,
    val cardNumber: Int,
    val expiryDate: String
) : Serializable
