package com.example.weatherapp.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.example.weatherapp.data.di.qualifers.IoDispatcher
import com.example.weatherapp.ui.presentacion.model.DeviceLocation
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FusedLocationProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val io: CoroutineDispatcher
) : LocationProvider {

    private val client = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getLastKnownLocation(): DeviceLocation = withContext(io) {
        val (lat, lon) = suspendCancellableCoroutine { cont ->
            client.lastLocation
                .addOnSuccessListener { loc ->
                    if (loc != null) cont.resume(loc.latitude to loc.longitude)
                    else cont.resumeWithException(IllegalStateException("No se pudo obtener ubicación"))
                }
                .addOnFailureListener { e -> cont.resumeWithException(e) }
        }

        val (locality, adminArea) = reverseGeocode(lat, lon)

        DeviceLocation(
            latitude = lat,
            longitude = lon,
            locality = locality,
            adminArea = adminArea
        )
    }

    private suspend fun reverseGeocode(lat: Double, lon: Double): Pair<String?, String?> {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val address = geocoder.getAddress(lat, lon)

            val city = address?.locality ?: address?.subAdminArea
            val state = address?.adminArea

            city to state
        } catch (e: Exception) {
            null to null
        }
    }

    suspend fun Geocoder.getAddress(
        lat: Double,
        lon: Double
    ): Address? = suspendCoroutine { continuation ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getFromLocation(lat, lon, 1) { addresses ->
                continuation.resume(addresses.firstOrNull())
            }
        } else {
            @Suppress("DEPRECATION")
            val address = try {
                getFromLocation(lat, lon, 1)?.firstOrNull()
            } catch (e: Exception) {
                null
            }
            continuation.resume(address)
        }
    }
}
