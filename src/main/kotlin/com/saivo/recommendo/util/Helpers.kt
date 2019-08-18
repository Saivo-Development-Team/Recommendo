package com.saivo.recommendo.util

import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

private const val algol = "RSA"

fun createUUID(func: () -> Boolean, value: String): String {
    return when {
        func() -> UUID.randomUUID().toString()
        else -> value
    }
}

fun <R> Throwable.multicatch(vararg classes: KClass<*>, block: () -> R): R {
    if (classes.any { this::class.isSubclassOf(it) }) {
        return block()
    } else throw this
}

@Throws(Exception::class)
fun encrypt(publicKey: ByteArray, data: ByteArray): ByteArray {
    val key = KeyFactory.getInstance(algol).generatePublic(X509EncodedKeySpec(publicKey))
    val cipher = Cipher.getInstance(algol)
    cipher.init(Cipher.ENCRYPT_MODE, key)
    return cipher.doFinal(data)
}

@Throws(Exception::class)
fun decrypt(privateKey: ByteArray, data: ByteArray): ByteArray {
    val key = KeyFactory.getInstance(algol).generatePrivate(PKCS8EncodedKeySpec(privateKey))
    val cipher = Cipher.getInstance(algol)
    cipher.init(Cipher.DECRYPT_MODE, key)
    return cipher.doFinal(data)
}

@Throws(NoSuchAlgorithmException::class, NoSuchProviderException::class)
fun generateKeyPair(): KeyPair {
    val keyGen = KeyPairGenerator.getInstance(algol)
    val random = SecureRandom.getInstance("SHA1PRNG", "SUN")
    keyGen.initialize(1024, random)
    return keyGen.generateKeyPair()
}