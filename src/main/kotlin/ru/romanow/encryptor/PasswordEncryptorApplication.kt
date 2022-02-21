package ru.romanow.encryptor

import java.security.MessageDigest
import java.util.*
import java.util.Arrays.copyOfRange
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.system.exitProcess

private const val ENCRYPT_ALGORITHM = "AES/CBC/PKCS5Padding"
private const val KEY_ALGORITHM = "AES"
private const val SALT_SIZE = 8
private const val SALT_PREFIX_LENGTH = 8

fun main(args: Array<String>?) {
    if (args?.size != 2) {
        println("usage: java -jar password-encryptor.jar <password> <data>")
        exitProcess(1)
    }
    val password = args[0]
    val data = args[1]
    println(decrypt(password, data))
}

fun decrypt(password: String, data: String): String {
    var decodedData = Base64.getDecoder().decode(data)
    if (!String(decodedData).startsWith("Salted__")) {
        throw RuntimeException("Missing salt")
    }

    val salt = copyOfRange(decodedData, SALT_PREFIX_LENGTH, SALT_PREFIX_LENGTH + SALT_SIZE)
    decodedData = copyOfRange(decodedData, SALT_PREFIX_LENGTH + SALT_SIZE, decodedData.size)

    val passAndSalt = concat(password.toByteArray(), salt)
    val digest = MessageDigest.getInstance("SHA-256")
    val key = digest.digest(passAndSalt)
    val secretKey = SecretKeySpec(key, KEY_ALGORITHM)
    val iv = copyOfRange(digest.digest(concat(key, passAndSalt)), 0, 16)

    val cipher = Cipher.getInstance(ENCRYPT_ALGORITHM)
    cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
    return String(cipher.doFinal(decodedData))
}

fun concat(a: ByteArray, b: ByteArray): ByteArray {
    val c = ByteArray(a.size + b.size)
    System.arraycopy(a, 0, c, 0, a.size)
    System.arraycopy(b, 0, c, a.size, b.size)
    return c
}
