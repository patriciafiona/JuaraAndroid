package com.patriciafiona.tentangku.data.source.remote.entity

data class Weather(
    val visibility: Int? = null,
    val timezone: Int? = null,
    val main: Main? = null,
    val clouds: Clouds? = null,
    val sys: Sys? = null,
    val dt: Int? = null,
    val coord: Coord? = null,
    val weather: List<WeatherItem?>? = null,
    val name: String? = null,
    val cod: Int? = null,
    val id: Int? = null,
    val base: String? = null,
    val wind: Wind? = null
)

data class Sys(
    val country: String? = null,
    val sunrise: Int? = null,
    val sunset: Int? = null,
    val id: Int? = null,
    val type: Int? = null
)

data class Clouds(
    val all: Int? = null
)

data class WeatherItem(
    val icon: String? = null,
    val description: String? = null,
    val main: String? = null,
    val id: Int? = null
)

data class Wind(
    val deg: Int? = null,
    val speed: Double? = null
)

data class Main(
    val temp: Double? = null,
    val tempMin: Double? = null,
    val humidity: Int? = null,
    val pressure: Int? = null,
    val feelsLike: Double? = null,
    val tempMax: Double? = null
)

data class Coord(
    val lon: Double? = null,
    val lat: Double? = null
)
