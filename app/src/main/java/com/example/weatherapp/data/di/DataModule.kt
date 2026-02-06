package com.example.weatherapp.data.di



import com.example.weatherapp.data.network.datasource.WeatherDataSource
import com.example.weatherapp.data.network.datasource.WeatherDataSourceImpl
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun binsWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    abstract fun bindWeatherRemoteDataSource(
        impl: WeatherDataSourceImpl
    ): WeatherDataSource


}
