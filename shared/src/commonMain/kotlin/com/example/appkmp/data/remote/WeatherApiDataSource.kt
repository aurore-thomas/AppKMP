package com.example.appkmp.data.remote

import com.example.appkmp.di.initKoin
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import com.example.appkmp.domain.model.Weather
import org.example.project.BuildConfig
import org.koin.mp.KoinPlatform

suspend fun main(){
    print("Entrez un texte : ")
    val cityAsked = readlnOrNull()

    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
    }

    initKoin()
    val weatherApiDataSource : WeatherApiDataSource =  KoinPlatform.getKoin().get<WeatherApiDataSource>()
    val city : List<Weather> = weatherApiDataSource.loadWeathers(cityAsked ?: "Toulouse")
    weatherApiDataSource.close()

    for(w in city) {
        println(w.getResume())
    }
}

//private val client  = HttpClient {
//    install(Logging) {
//        //(import io.ktor.client.plugins.logging.Logger)
//        logger = object : Logger {
//            override fun log(message: String) {
//                println(message)
//            }
//        }
//        level = LogLevel.INFO  // TRACE, HEADERS, BODY, etc.
//    }
//    install(ContentNegotiation) {
//        json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
//    }
//    install(HttpTimeout) {
//        requestTimeoutMillis = 5000
//    }
//    //engine { proxy = ProxyBuilder.http("monproxy:1234") }
//}

// On met en object pour éviter d'avoir à créer une instance à chaque fois
class WeatherApiDataSource(val client: HttpClient) {
    private val API_URL = BuildConfig.WEATHER_API_KEY

    //Retoure la liste des points météos de la ville en paramètre
    suspend fun loadWeathers(cityName:String) : List<Weather> {
        if (cityName == null || cityName.length < 3) {
            throw Exception ("Ville incorrecte")
        }

        val response = client.get(API_URL){
            parameter("q", cityName)
        }

        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        val result = response.body<WeatherCompleteDto>()
        val responseList = result.list ?: emptyList()



        val weatherList = responseList.map { city ->
            Weather(
                id = city.id ?: 0,
                name = city.name ?: "-",
                temp = city.main?.temp ?: 0.0,
                speed = city.wind?.speed ?: 0.0,
                description = city.weather?.firstOrNull()?.description ?: "-",
                icon = city.weather?.firstOrNull()?.icon?.let { "https://openweathermap.org/img/wn/$it@4x.png" } ?: "-"
            )
        }

        return weatherList
    }

    fun close() = client.close()
}

@Serializable
data class WeatherCompleteDto(
    val list: List<CityDto>? = null
)

@Serializable
data class CityDto(
    val id: Int? = null,
    val name: String? = null,
    val main: MainDto? = null,
    val wind: WindDto? = null,
    val weather: List<WeatherDto>? = null
)

@Serializable
data class MainDto(
    val temp: Double? = null
)

@Serializable
data class WindDto(
    val speed: Double? = null
)

@Serializable
data class WeatherDto(
    val description: String? = null,
    val icon: String? = null
)

