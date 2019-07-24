package com.example.dig.sawtooth

import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.db.entity.Vote
import com.example.dig.data.network.post.Transaction as PostTransaction

interface DignitasHelper {

    fun createVote(vote: Vote) : PostTransaction

    fun createOpinion(opinion: Opinion) : PostTransaction
}
