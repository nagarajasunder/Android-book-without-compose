package com.geekydroid.androidbook

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoManager @Inject constructor(private val preferences:DataStore<Preferences>) {

    private val keyName = stringPreferencesKey("secure_key")

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODES = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODES/$PADDING"
        private const val KEY_ALIAS = "MASTER_KEY"
    }

    private val IV_SEPERATOR: String = "]"
    private val keystore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val cipher by lazy {
        Cipher.getInstance(TRANSFORMATION)
    }

    private fun getKey():SecretKey
    {
        return (keystore.getKey(KEY_ALIAS,null) as SecretKey?)?:createKey()
    }

    private fun createKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM, "AndroidKeyStore")
        val builder = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(BLOCK_MODES)
            .setEncryptionPaddings(PADDING)
            .setUserAuthenticationRequired(false)
        keyGenerator.init(builder.build())
        return keyGenerator.generateKey()
    }

    suspend fun storeSecuredData(data:String)
    {
        preferences.edit {
            it[keyName] = encrypt(data)
        }
    }

    suspend fun secureFetch(): Flow<String> {
        return preferences.data.map {
            val data = it[keyName] ?: ""
            decrypt(data)
        }
    }

    private fun encrypt(data:String):String
    {
        cipher.init(Cipher.ENCRYPT_MODE,getKey())
        var result = ""
        val iv = cipher.iv
        val ivString = Base64.encodeToString(iv,Base64.DEFAULT)
        result = ivString+IV_SEPERATOR
        val bytes = cipher.doFinal(data.toByteArray())
        result+=Base64.encodeToString(bytes,Base64.DEFAULT)
        return result
    }

    private fun decrypt(data:String):String
    {
        var encodedString = ""
        val split = data.split(IV_SEPERATOR.toRegex())
        val ivString = split[0]
        encodedString = split[1]
        val ivSpec = IvParameterSpec(Base64.decode(ivString,Base64.DEFAULT))
        cipher.init(Cipher.DECRYPT_MODE,getKey(),ivSpec)
        val encryptedData = Base64.decode(encodedString,Base64.DEFAULT)
        val decodedData = cipher.doFinal(encryptedData)
        return String(decodedData)
    }
}