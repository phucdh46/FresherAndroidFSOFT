package com.example.kotlinbasics.day5

import java.lang.NumberFormatException
import java.util.*

class ManageHostConfig {
    private var listHostConfig = mutableListOf<HostConfig>()
    private lateinit var typeConnection: TypeConnection

    private fun addHostConfig(hostConfig: HostConfig) {
        listHostConfig.add(hostConfig)
    }
    //enter HostConfig (min 4 items) include ip, port, type connection
    fun enterHostConfig() {
        var n: Int
        //check number of host config input
        while (true) {
            print("Please enter the number of host config (min 4 items) : ")
            n = try {
                readLine()?.toInt() ?:0
            } catch (e: NumberFormatException) {
                println("Please input number")
                -1
            }
            when {
                n > 3 -> break
                else -> continue
            }
        }

        //enter ip, port, type connection
        for (i in 0 until n) {
            println("Host Config: ${(i+1)} ")
            print("Ip : ")
            val ip = readLine().toString()
            print("Port : ")
            val port = readLine().toString().toInt()
            typeConnection = enterTypeConnection()

            //add Host config automatically with port + 1
            addHostConfig(HostConfig(ip, port, typeConnection))
            addHostConfig(HostConfig(ip, port.plus(1), typeConnection))
            addHostConfig(HostConfig(ip, port.plus(2), typeConnection))
            addHostConfig(HostConfig(ip, port.plus(3), typeConnection))

        }
    }
    // get all host config
    fun getAllHostConfig() {
        for (i in 0 until listHostConfig.size) {
            println(listHostConfig[i])
        }
    }
    // get host config by host config
    fun getListOfHostConfigByHost() {
        print("Enter ip: ")
        val ip = readLine().toString()
        print("Enter port: ")
        val port = readLine().toString().toInt()
        val typeConnection: TypeConnection = enterTypeConnection()

        val host: HostConfig = HostConfig(ip,port,typeConnection)
        if (host in listHostConfig) {
            println(host)
        } else {
            println("No host config in list")
        }
    }
    // get list host config by ip
    fun getListOfHostConfigByIp() {
        print("Enter ip : ")
        val ip = readLine().toString()
        var flag: Int = 0
        for (i in 0 until listHostConfig.size) {
            if (listHostConfig[i].ip == ip) {
                println(listHostConfig[i])
                flag++
            }
        }
        if (flag==0) println("No host config with ip : $ip")
    }
    // get list host config by port
    fun getListOfHostConfigByPort() {
        print("Enter port : ")
        val port = readLine()?.toInt()
        var flag: Int = 0
        for (i in 0 until listHostConfig.size) {
            if (listHostConfig[i].port == port) {
                println(listHostConfig[i])
                flag++
            }
        }
        if (flag==0) println("No host config with port : $port")
    }
    // get host config by type connection
    fun getListOfHostConfigByTypeConnection() {
        val typeConnection: TypeConnection = enterTypeConnection()
        for (i in 0 until listHostConfig.size) {
            if (listHostConfig[i].typeConnection == typeConnection) {
                println(listHostConfig[i])
            }
        }
    }
    // enter type connection with Wifi/Data 4g/Any
    private fun enterTypeConnection() :TypeConnection {
        var typeConnection : TypeConnection
        while (true) {
            print("Enter type Connection (Wifi/Data 4g/Any) : ")
            when (readLine().toString().lowercase(Locale.getDefault())) {
                "wifi" -> {
                    typeConnection = TypeConnection.WIFI
                    break
                }
                "data 4g", "4g" -> {
                    typeConnection = TypeConnection.DATA_4G
                    break
                }
                "any" -> {
                    typeConnection = TypeConnection.ANY
                    break
                }
                else -> continue
            }
        }
        return typeConnection
    }
}