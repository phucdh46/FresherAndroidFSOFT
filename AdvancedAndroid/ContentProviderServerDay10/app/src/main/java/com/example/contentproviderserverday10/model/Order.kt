package com.example.contentproviderserverday10.model

import java.io.Serializable

data class Order(
    val id: Int?,
    val customer_name: String,
    val timestamp: String,
    val value: String
) : Serializable