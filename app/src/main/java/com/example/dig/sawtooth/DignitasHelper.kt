package com.example.dig.sawtooth

import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.db.entity.Vote
import com.google.protobuf.ByteString
import com.example.dig.data.network.post.Transaction as PostTransaction

interface DignitasHelper {

    fun createVote(vote: Vote) : ByteArray

    fun createOpinion(opinion: Opinion) : ByteArray

    fun retrieveWalletAddress() : String
}
