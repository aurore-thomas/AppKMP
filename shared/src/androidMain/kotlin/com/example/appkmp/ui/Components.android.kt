package com.example.appkmp.ui

import android.R.attr.data
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appkmp.shared.generated.resources.Res
import appkmp.shared.generated.resources.error
import coil3.compose.AsyncImage
import com.example.appkmp.domain.model.Weather
import org.jetbrains.compose.resources.painterResource

@Composable
actual fun WeatherGallery(
    modifier: Modifier,
    list: List<Weather>,
    onPictureClick: (Weather) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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