package com.example.dig.data.provider

import sawtooth.sdk.signing.PrivateKey

interface KeysProvider {
    fun getPrivateKey() : PrivateKey
    fun getOBUPublicKey() : String
}