package com.example.weather.network

import com.example.weather.data.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Query

//
// HOST: https://api.nasa.gov/
// PATH: mars-photos/api/v1/rovers/curiosity/photos
// QUERY PARAMS:
// ?
// earth_date=2024-1-4
// &
// api_key=DEMO_KEY

//HOST: https://api.openweathermap.org/
// PATH: data/2.5/weather
// QUERY PARAMS:
// ?
// q=Budapest,hu
// &
// units=metric
// &
// appid=808ec91591e0ff979a1e618ee2462adb

interface WeatherAPI {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ) : WeatherResult
}