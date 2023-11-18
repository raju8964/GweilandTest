package com.cord.simpletest.utils

import android.util.Base64
import androidx.constraintlayout.widget.Constraints
import com.cord.simpletest.data.SharedPref
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

fun encrypt(message: String): String {
    try {
        val srcBuff = message.toByteArray(charset("UTF8"))
        val skeySpec = SecretKeySpec(Base64.decode(SharedPref.getData(Common.AES_KEY), Base64.DEFAULT), "AES")
        val ivSpec = IvParameterSpec(Base64.decode(SharedPref.getData(Common.AES_IV), Base64.DEFAULT))
        val ecipher: Cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        ecipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec)
        val dstBuff: ByteArray = ecipher.doFinal(srcBuff)
        return Base64.encodeToString(dstBuff, Base64.DEFAULT)
    } catch (ex: Exception) {
    }
    return ""
}
fun decrypt(encrypted: String): String? {
    try {
        val skeySpec = SecretKeySpec(Base64.decode(SharedPref.getData(Common.AES_KEY), Base64.DEFAULT), "AES")
        val ivSpec = IvParameterSpec(Base64.decode(SharedPref.getData(Common.AES_IV), Base64.DEFAULT))
        val ecipher: Cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        ecipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec)
        val raw: ByteArray = Base64.decode(encrypted, Base64.DEFAULT)
        val originalBytes: ByteArray = ecipher.doFinal(raw)
        return String(originalBytes, Charset.forName("UTF-8"))
    } catch (ex: Exception) {
    }
    return null
}