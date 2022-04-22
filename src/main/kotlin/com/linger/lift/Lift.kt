package com.linger.lift

import com.linger.lift.util.TweetNaclFast
import org.bitcoinj.core.Base58
import org.bitcoinj.core.Sha256Hash
import java.io.ByteArrayOutputStream

object Lift {
    fun generateAddress(vararg seeds: ByteArray, programId: String): String {
        val header = concat(*seeds, limit = 32)
        val tail = concat(
            Base58.decode(programId),
            "ProgramDerivedAddress".toByteArray()
        )

        var nonce = 255
        while (nonce != 0) {
            try {
                val picked = concat(
                    header,
                    byteArrayOf(nonce.toByte()),
                    tail
                )
                val hash = Sha256Hash.hash(picked)
                if (TweetNaclFast.is_on_curve(hash) != 0) {
                    nonce--
                    continue
                }

                return Base58.encode(hash)
            } catch (e: Exception) {
                nonce--
            }
        }

        throw Exception("Unable to find a viable program address nonce")
    }

    private fun concat(
        vararg seeds: ByteArray,
        limit: Int = Int.MAX_VALUE,
    ): ByteArray {
        ByteArrayOutputStream().use { os ->
            seeds.forEach {
                if (it.size > limit) throw IllegalAccessException("Max seed length exceeded")
                os.writeBytes(it)
            }
            return os.toByteArray()
        }
    }
}