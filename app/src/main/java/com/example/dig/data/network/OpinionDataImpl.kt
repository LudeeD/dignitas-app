package com.example.dig.data.network

import android.util.Log
import com.example.dig.data.db.entity.Opinion
import com.example.dig.internal.NoConnectivityException
import com.example.dig.sawtooth.DignitasHelper

class OpinionDataImpl(
    private val apiService: ApiService,
    private val dignitasHelper : DignitasHelper
) : OpinionData {

    override suspend fun postOpinions(opinion: Opinion) {
        try{
            val transaction = dignitasHelper.createOpinion(opinion)
            apiService.postOpinions(transaction)
        } catch(e : NoConnectivityException){
            Log.e("Connectivity", "No Internet", e)
        }
    }

}