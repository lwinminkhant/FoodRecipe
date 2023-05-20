package com.lmkhant.foodrecipedb.common.network


interface NetworkConnectivityInterceptor{
    fun isConnected():Boolean
    fun isServerAvailable(): String
}