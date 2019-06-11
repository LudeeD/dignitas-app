package com.example.dig.data.network

import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.network.response.OpinionResponse
import com.example.dig.data.network.response.VotesResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // https://www.youtube.com/watch?v=RSYTn-O3r34&list=PLB6lc7nQ1n4jTLDyU2muTBo8xk0dg0D_w&index=2
    @GET("votes")
    fun getVotes() : Deferred<VotesResponse>

    @POST("opinion")
    fun postOpinions(@Body opinions: Opinion) : Deferred<OpinionResponse>

    companion object {
        operator fun invoke(
           connectivityInterceptor: ConnectivityInterceptor
        ): ApiService {

            val client = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl("http://10.0.2.2:3000/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}