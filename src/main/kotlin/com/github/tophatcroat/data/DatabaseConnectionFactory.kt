package com.github.tophatcroat.data

interface DatabaseConnectionFactory {
    fun connect()
    fun disconnect()
}
