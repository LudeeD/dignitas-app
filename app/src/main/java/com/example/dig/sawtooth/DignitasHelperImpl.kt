package com.example.dig.sawtooth

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.dig.data.db.entity.Opinion
import com.example.dig.data.db.entity.Vote
import com.example.dig.data.network.post.Transaction as PostTransaction
import com.example.dig.data.provider.KeysProvider
import com.example.dig.internal.SigningError
import com.google.protobuf.ByteString
import sawtooth.sdk.protobuf.Transaction
import sawtooth.sdk.protobuf.TransactionHeader
import sawtooth.sdk.signing.PublicKey

import sawtooth.sdk.signing.Signer
import sawtooth.sdk.signing.Secp256k1Context
import sawtooth.sdk.signing.Secp256k1PublicKey
import java.security.MessageDigest
import java.util.*
import kotlin.random.Random

class DignitasHelperImpl(

    keyProvider: KeysProvider

) : DignitasHelper {

    private var signer: Signer? = null
    private var batcher_pubkey: PublicKey? = null

    init {
        val context = Secp256k1Context()
        this.signer = Signer(context, keyProvider.getPrivateKey())
        batcher_pubkey  = Secp256k1PublicKey.fromHex(keyProvider.getOBUPublicKey());
    }

    override fun retrieveWalletAddress(): String {
        return get_wallet_address(this.signer!!.publicKey.hex())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createVote(vote: Vote): ByteArray {
        val array_info = arrayOf("CreateVote", "", "",
            vote.title, vote.info,
            vote.location.lat.toString(),
            vote.location.lng.toString(),
            vote.location.direction.toString()
        )

        val payload_string = array_info.joinToString(",")

        val nonce = Random.nextDouble()

        val pubkey = signer?.publicKey?.hex()

        if (pubkey.isNullOrEmpty()){
            throw SigningError()
        }

        Log.v("Fuck my life", this.batcher_pubkey?.hex());

        val header = TransactionHeader.newBuilder()
            .setSignerPublicKey(pubkey)
            .setFamilyName("dignitas")
            .setFamilyVersion("1.0")
            .addInputs(get_vote_address(pubkey))
            .addInputs(get_wallet_address(pubkey))
            .addOutputs(get_vote_address(pubkey))
            .addOutputs(get_wallet_address(pubkey))
            .setPayloadSha512(hash(payload_string))
            .setBatcherPublicKey(this.batcher_pubkey?.hex())
            .setNonce(nonce.toString())
            .build()

        val signature = this.signer?.sign(header.toByteArray())

        val transaction = Transaction.newBuilder()
            .setHeader(header.toByteString())
            .setPayload(ByteString.copyFrom(payload_string, "UTF-8"))
            .setHeaderSignature(signature)
            .build()


        return transaction.toByteArray()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createOpinion(opinion: Opinion): ByteArray {
        val array_info = arrayOf("Vote",opinion.voteId.toString(), opinion.value.toString())

        val payload_string = array_info.joinToString(",")
        //Log.v("GOOOOOOOOOOOOOD", payload_string)

        val nonce = Random.nextDouble()

        val pubkey = signer?.publicKey?.hex()

        if (pubkey.isNullOrEmpty()){
            throw SigningError()
        }

        Log.v("Fuck my life", this.batcher_pubkey?.hex());

        val header = TransactionHeader.newBuilder()
            .setSignerPublicKey(pubkey)
            .setFamilyName("dignitas")
            .setFamilyVersion("1.0")
            .addInputs(get_vote_address(pubkey))
            .addInputs(get_wallet_address(pubkey))
            .addOutputs(get_vote_address(pubkey))
            .addOutputs(get_wallet_address(pubkey))
            .setPayloadSha512(hash(payload_string))
            .setBatcherPublicKey(this.batcher_pubkey?.hex())
            .setNonce(nonce.toString())
            .build()

        val signature = this.signer?.sign(header.toByteArray())

        val transaction = Transaction.newBuilder()
            .setHeader(header.toByteString())
            .setPayload(ByteString.copyFrom(payload_string, "UTF-8"))
            .setHeaderSignature(signature)
            .build()


        return transaction.toByteArray()
    }



    private fun get_sw_prefix(): String{
        return "ce9618"
    }

    private fun get_wallets_prefix(): String{
        return get_sw_prefix() + "00"
    }


    private fun get_wallet_address(pubkey: String): String {
        return get_wallets_prefix() +  hash(pubkey).take(62)
    }

    private fun get_vote_address(pubkey: String): String {
        return get_votes_prefix()
    }

    private fun get_votes_prefix(): String{
        return get_sw_prefix() + "01"
    }

    private fun hash(data:String) : String{

        val bytes = data.toByteArray()

        val md = MessageDigest.getInstance("SHA-512")

        val digest = md.digest(bytes)

        val string_digest = digest.fold("", { str, it -> str + "%02x".format(it) } )

        return string_digest
    }

}