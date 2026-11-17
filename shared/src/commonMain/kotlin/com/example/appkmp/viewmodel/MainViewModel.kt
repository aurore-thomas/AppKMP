package com.example.appkmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appkmp.data.remote.WeatherApiDataSource
import com.example.appkmp.di.initKoin
import com.example.appkmp.domain.model.Weather
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.mp.KoinPlatform

suspend fun main() {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
    }

    initKoin()
    val weatherApiDataSource : WeatherApiDataSource =  KoinPlatform.getKoin().get<WeatherApiDataSource>()

    val viewModel = MainViewModel(weatherApiDataSource)

    viewModel.loadWeathers("Sorbiers")
    while (viewModel.runInProgress.value) {
        println("Attente...")
        delay(500)
    }
    println("List : ${viewModel.dataList.value}")
    println("ErrorMessage : ${viewModel.errorMessage.value}")

    weatherApiDataSource.close()
}

class MainViewModel(val weatherApiDataSource: WeatherApiDataSource) : ViewModel() {
    val dataList = MutableStateFlow(emptyList<Weather>())
    var runInProgress = MutableStateFlow(false)
    var errorMessage = MutableStateFlow("")

    fun loadWeathers(cityName: String) {
        errorMessage.value = ""
        runInProgress.value = true

       viewModelScope.launch(Dispatchers.IO) {
            try {
                dataList.value = weatherApiDataSource.loadWeathers(cityName)
            } catch (e: Exception) {
                errorMessage.value = e.message.toString();
                e.printStackTrace()
            }
            runInProgress.value = false
        }
    }
}