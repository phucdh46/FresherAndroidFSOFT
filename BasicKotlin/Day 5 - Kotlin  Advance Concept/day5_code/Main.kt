package com.example.kotlinbasics.day5

import java.util.*

fun main() {
    
    val manageHostConfig = ManageHostConfig()
    manageHostConfig.enterHostConfig()
    while (true) {
        while (true) {
            println("----------------------------------------------------------------------------------")
            println("Show all host config with condition (ip/ port/ type connection/ host config/ none)")
            when (readLine().toString().lowercase(Locale.getDefault())) {
                "ip" -> {
                    manageHostConfig.getListOfHostConfigByIp()
                    break
                }
                "port" -> {
                    manageHostConfig.getListOfHostConfigByPort()
                    break
                }
                "type connection" -> {
                    manageHostConfig.getListOfHostConfigByTypeConnection()
                    break
                }
                "host config" -> {
                    manageHostConfig.getListOfHostConfigByHost()
                    break
                }
                "none" -> {
                    manageHostConfig.getAllHostConfig()
                    break
                }
                else -> continue
            }
        }
    }
}