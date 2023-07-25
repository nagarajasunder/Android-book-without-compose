package com.geekydroid.androidbook


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import kotlin.random.Random

object LocationProvider {

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private var locationResultCallback: LocationResultCallback? = null
    private const val intervalMills = 15_00L
    private const val minUpdateIntervalMills = 20_00L
    private var lastKnownLocation: Location? = null
    private var appContext:Context? = null

    private var executeFlow = true

    fun init(context: Context) {
        executeFlow = true
        appContext = context
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        createLocationRequest()
        getLastKnownLocation(context)
    }

    fun coldFlow() = callbackFlow<Int> {
        startColdFlowUpdates()
        awaitClose {
            Toast.makeText(appContext,"Await close called",Toast.LENGTH_SHORT).show()
            executeFlow = false
        }
    }

    private suspend fun startColdFlowUpdates() {
        while (executeFlow) {
            delay(1500)
            val randomInt = Random.nextInt()
            Toast.makeText(appContext,"Producing result $randomInt",Toast.LENGTH_SHORT).show()
        }
    }


    private fun checkIfLocationPermissionGranted(context: Context): Boolean {
        val foregroundLocationGranted = (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
        val backgroundLocationGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        } else {
            true
        }
        return foregroundLocationGranted && backgroundLocationGranted
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(context: Context) {
        if (checkIfLocationPermissionGranted(context)) {
            fusedLocationClient?.lastLocation?.addOnSuccessListener {
                lastKnownLocation = it
            }
        }

    }

    private fun createLocationRequest() {
        val locationRequestBuilder = LocationRequest.Builder(
            PRIORITY_BALANCED_POWER_ACCURACY,
            intervalMills
        )
        locationRequestBuilder.setIntervalMillis(intervalMills)
        locationRequestBuilder.setMinUpdateIntervalMillis(minUpdateIntervalMills)
        locationRequest = locationRequestBuilder.build()

    }

    fun setLocationUpdatesCallback(callback: LocationResultCallback) {
        locationResultCallback = callback
    }

    @SuppressLint("MissingPermission")
    fun locationFlow() = callbackFlow<Location?> {
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                result ?: return
                try {
                    Toast.makeText(appContext!!,"Produced location updates",Toast.LENGTH_SHORT).show()
                    trySend(result.lastLocation).isSuccess
                } catch (_: Exception) {
                }
            }
        }
        locationCallback = callback
        fusedLocationClient!!.requestLocationUpdates(locationRequest!!,callback, Looper.getMainLooper())
        awaitClose {
            Toast.makeText(appContext,"Await close called",Toast.LENGTH_SHORT).show()
            stopLocationUpdates()
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(context: Context) {
        if (checkIfLocationPermissionGranted(context)) {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    lastKnownLocation = locationResult.lastLocation
                    locationResultCallback?.onLocationUpdated(locationResult)
                }

            }
//            fusedLocationClient?.requestLocationUpdates(
//                locationRequest!!,
//                locationCallback!!,
//                Looper.getMainLooper()
//            )
        }
    }

    fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient?.removeLocationUpdates(it) }
    }

}

interface LocationResultCallback {
    fun onLocationUpdated(locationResult: LocationResult)
}