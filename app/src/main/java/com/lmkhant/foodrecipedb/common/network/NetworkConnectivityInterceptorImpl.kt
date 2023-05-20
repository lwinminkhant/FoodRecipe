package com.lmkhant.foodrecipedb.common.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.lmkhant.foodrecipedb.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.HttpURLConnection
import java.net.URL


class NetworkConnectivityInterceptorImpl(@ApplicationContext private val  context: Context) : NetworkConnectivityInterceptor{

    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    override fun isServerAvailable(): String {
        return try {
            val url = URL(Constants.BASE_URL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                "OK"
            } else {
                "server is not responding. Response code: $responseCode"
            }
        } catch (e: Exception) {
            "API server is not responding. Error: ${e.message}"
        }
    }
}
