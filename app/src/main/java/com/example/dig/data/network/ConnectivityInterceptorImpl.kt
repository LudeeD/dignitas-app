package com.example.dig.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.dig.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isPhoneOnline())
            throw NoConnectivityException()

        return tryWithCare(chain) ?: throw NoConnectivityException()
    }


    private fun isPhoneOnline(): Boolean{
        val conManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = conManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }

    private fun tryWithCare(chain: Interceptor.Chain): Response?{
        val request = chain.request();
        try {
            val response = chain.proceed(request)
            Log.v("PILAAA", chain.request().url().toString())
            return response
        }catch (e: Exception){
            Log.v("PILAAA", chain.request().url().toString())
            return null
        }
    }

}