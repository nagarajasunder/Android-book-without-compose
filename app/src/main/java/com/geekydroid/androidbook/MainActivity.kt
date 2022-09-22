package com.geekydroid.androidbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.geekydroid.androidbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val viewmodel:MainViewmodel by viewModels()
    @Inject
    lateinit var cryptoManager: CryptoManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(binding.root)
        binding.model = viewmodel
        binding.lifecycleOwner = this

        binding.btnStore.setOnClickListener {
            storeEncryptedData()
        }
        binding.btnRetrieve.setOnClickListener {
            retrieveEncryptedData()
        }

    }

    private fun retrieveEncryptedData() {
        //val value = EncryptedSharedPrefManager.getStringValue(applicationContext,"access_token")
        val file = File(filesDir,"secret.txt")
        val messageDecrypted = cryptoManager.decrypt(FileInputStream(file)).decodeToString()
        viewmodel.updateValue(messageDecrypted)
    }

    private fun storeEncryptedData() {
        val value = viewmodel.valueToStore.value
        val bytes = value!!.encodeToByteArray()
        val file = File(filesDir,"secret.txt")
        if (!file.exists())
        {
            file.createNewFile()
        }
        val fos = FileOutputStream(file)
        val encryptedMessage = cryptoManager.encrypt(bytes,fos).decodeToString()
        viewmodel.updateValue(encryptedMessage)
        //EncryptedSharedPrefManager.saveString(applicationContext,"access_token",value!!)
    }
}