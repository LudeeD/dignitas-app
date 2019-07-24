package com.example.dig.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import sawtooth.sdk.signing.PrivateKey
import sawtooth.sdk.signing.Secp256k1Context

class KeysProviderImpl(context: Context) : KeysProvider {


    private val appContext = context.applicationContext
    private var pk: PrivateKey? = null

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    init {
        val ctx = Secp256k1Context()
        this.pk = ctx.newRandomPrivateKey()
    }

    override fun getOBUPublicKey(): String {
        val obu_pub_key = preferences.getString("OBU_PK", "02381caa0892d913daa3c4856a4f9b665931964b3fc630ef9dd5edbd8a27952f7e")!!
        Log.v("I will return", obu_pub_key)
        return obu_pub_key
    }

    override fun getPrivateKey(): PrivateKey {
        return this.pk!!
    }
}