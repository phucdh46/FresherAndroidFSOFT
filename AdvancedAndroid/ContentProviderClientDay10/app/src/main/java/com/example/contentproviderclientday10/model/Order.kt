package com.example.contentproviderclientday10.model

data class Order(
    val id: Int,
    val customer_name: String,
    var timestamp: String,
    val value: String
)