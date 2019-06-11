package com.example.dig.data.db.entity


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("type")
    val typeX: String,
    @SerializedName("false")
    val falseX: Int,
    @SerializedName("true")
    val trueX: Int,
    val verdict: String
)