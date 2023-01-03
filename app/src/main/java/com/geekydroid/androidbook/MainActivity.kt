package com.geekydroid.androidbook

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.geekydroid.androidbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var storageType = StorageType.APP_SPECIFIC_INTERNAL_STORAGE
    private var imageTempFile: File? = null
    private var imageUri: Uri? = null
    private var currentImageFileName: String? = null
    private lateinit var binding: ActivityMainBinding
    private val sharedStorageResultLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { resultBitmap ->
        resultBitmap?.let { bitmap ->
            updateSharedStorageCapturedImage(bitmap)
            saveImageToSharedStorage(bitmap)
        }
    }

    private fun saveImageToSharedStorage(bitmap: Bitmap) {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME,SimpleDateFormat("ddMMyyyy_HHmmss", Locale.ENGLISH).format(Date()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_DCIM)
            }
        }
        val resolver = applicationContext.contentResolver
        var uri:Uri? = null
        try {
            uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
                ?: throw IOException("Failed to create new MediaStore record")
            resolver?.openOutputStream(uri)?.use {
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG,95,it)) {
                    throw IOException("Failed to save bitmap.")
                }
            } ?: throw IOException("Failed to open output stream")
        } catch (e:IOException) {
            uri?.let { orphanUri ->
                resolver.delete(orphanUri,null,null)
            }
            throw e
        }

    }

    private fun updateSharedStorageCapturedImage(bitmap: Bitmap) {
        Glide.with(binding.root).load(bitmap).into(binding.ivImageViewer)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                updateCapturedImage()
            } else {
                Toast.makeText(this, "Error Capturing Image", Toast.LENGTH_SHORT).show()
            }
        }

    private fun updateCapturedImage() {
        Glide.with(this).load(imageUri).into(binding.ivImageViewer)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        binding.btnCapture.setOnClickListener {
            if (storageType == StorageType.SHARED_STORAGE) {
                launchSharedStorageCamera()
            }
            else {
                launchCamera()
            }
        }
        binding.btnLoad.setOnClickListener {
            when(storageType) {
                StorageType.APP_SPECIFIC_INTERNAL_STORAGE ->  {
                    loadImageFromAppSpecificInternalStorage()
                }
                StorageType.APP_SPECIFIC_EXTERNAL_STORAGE ->  {
                    loadImageFromAppSpecificExternalStorage()
                }
                StorageType.SHARED_STORAGE -> {

                }
            }
        }

        binding.btnClear.setOnClickListener {
            Glide.with(binding.root).load("").into(binding.ivImageViewer)
        }

        binding.radioGroupAppSpecific.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radio_internal_storage) {
                storageType = StorageType.APP_SPECIFIC_INTERNAL_STORAGE
            }
            else if (checkedId == R.id.radio_external_storage) {
                storageType = StorageType.APP_SPECIFIC_EXTERNAL_STORAGE
            }
        }
    }

    private fun launchSharedStorageCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        sharedStorageResultLauncher.launch()
    }

    private fun loadImageFromAppSpecificExternalStorage() {
        val file = currentImageFileName?.let { it1 -> File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), it1) }
        file?.let {
            Glide.with(binding.root).load(it).into(binding.ivImageViewer)
        }
    }

    private fun loadImageFromAppSpecificInternalStorage() {
        val file = currentImageFileName?.let { it1 -> File(cacheDir, it1) }
        file?.let {
            Glide.with(binding.root).load(it).into(binding.ivImageViewer)
        }
    }

    private fun launchCamera() {
        imageTempFile = when(storageType) {
            StorageType.APP_SPECIFIC_INTERNAL_STORAGE -> {
                createImageAppSpecificInternalStorage()
            }
            StorageType.APP_SPECIFIC_EXTERNAL_STORAGE -> {
                createImageAppSpecificExternalStorage()
            }
            StorageType.SHARED_STORAGE -> {
                createImageSharedStorage()
            }
        }
        if (storageType == StorageType.APP_SPECIFIC_EXTERNAL_STORAGE) {
            Toast.makeText(this,imageTempFile!!.absolutePath,Toast.LENGTH_SHORT).show()
        }

        imageTempFile?.let { file ->
            imageUri =
                FileProvider.getUriForFile(
                    applicationContext,
                    "${packageName}.provider",
                    file
                )
        }

        resultLauncher.launch(imageUri)
    }

    private fun createImageSharedStorage(): File {
        val timestamp = SimpleDateFormat("ddMMyyyy_HHmmss", Locale.ENGLISH).format(Date())
        currentImageFileName = "JPEG_$timestamp.jpg"
        return File(cacheDir, currentImageFileName!!)
    }

    private fun createImageAppSpecificExternalStorage(): File {
        val timestamp = SimpleDateFormat("ddMMyyyy_HHmmss", Locale.ENGLISH).format(Date())
        currentImageFileName = "JPEG_$timestamp.jpg"
        return File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), currentImageFileName!!)
    }


    private fun createImageAppSpecificInternalStorage(): File {
        val timestamp = SimpleDateFormat("ddMMyyyy_HHmmss", Locale.ENGLISH).format(Date())
        currentImageFileName = "JPEG_$timestamp.jpg"
        return File(cacheDir, currentImageFileName!!)
    }
}

enum class StorageType {
    APP_SPECIFIC_INTERNAL_STORAGE,
    APP_SPECIFIC_EXTERNAL_STORAGE,
    SHARED_STORAGE
}