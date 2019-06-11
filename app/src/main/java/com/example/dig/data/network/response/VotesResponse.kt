package com.example.dig.data.network.response


import com.example.dig.data.db.entity.Vote

data class VotesResponse(
    val votes: List<Vote>
)