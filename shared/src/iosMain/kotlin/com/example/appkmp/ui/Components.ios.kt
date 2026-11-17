package com.example.appkmp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.appkmp.domain.model.Weather

@Composable
actual fun WeatherGallery(
    modifier: Modifier,
    list: List<Weather>,
    onPictureClick: (Weather) -> Unit
) {
}