package com.geekydroid.androidbook

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

object EncryptedSharedPrefManager {


    val sharedPrefFileName = "ANDROID_SECURE"
    val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    fun saveString(context:Context,key:String, valueToStore: String) {
        val prefs = getSharedPreferences(context)
        with(prefs.edit()){
            putString(key,valueToStore)
            apply()
        }
    }

    fun getStringValue(context: Context,key:String):String
    {
        val prefs = getSharedPreferences(context)
        return prefs.getString(key,"")?:""
    }


    fun getSharedPreferences(context: Context): SharedPreferences =
        EncryptedSharedPreferences.create(
            sharedPrefFileName,
            masterKey,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

}