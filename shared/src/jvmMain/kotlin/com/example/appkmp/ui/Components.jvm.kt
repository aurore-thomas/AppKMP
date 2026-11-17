package com.example.appkmp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appkmp.domain.model.Weather

@Composable
actual fun WeatherGallery(
    modifier: Modifier,
    list: List<Weather>,
    onPictureClick: (Weather) -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = list,
            key = { weather -> weather.id }
        ) { weather ->
            PictureRowItem(
                modifier = Modifier.width(280.dp),
                data = weather,
                onPictureClick = onPictureClick
            )
        }
    }
}