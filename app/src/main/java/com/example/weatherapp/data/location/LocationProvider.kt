package com.example.weatherapp.data.location

import com.example.weatherapp.ui.presentacion.model.DeviceLocation

interface LocationProvider {
    suspend fun getLastKnownLocation(): DeviceLocation

}