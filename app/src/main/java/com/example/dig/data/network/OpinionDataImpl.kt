package com.example.dig.data.network

import android.util.Log
import com.example.dig.data.db.entity.Opinion
import com.example.dig.internal.NoConnectivityException

class OpinionDataImpl(
    private val apiService: ApiService
) : OpinionData {

    override suspend fun postOpinions(opinion: Opinion) {
        try{
            apiService.postOpinions(opinion)
        } catch(e : NoConnectivityException){
            Log.e("Connectivity", "No Internet", e)
        }
    }

}