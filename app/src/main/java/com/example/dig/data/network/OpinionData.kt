package com.example.dig.data.network

import com.example.dig.data.db.entity.Opinion

interface OpinionData {
    suspend fun postOpinions(opinion: Opinion)
}