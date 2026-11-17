package com.example.appkmp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
fun MyError(
    modifier: Modifier = Modifier,
    errorMessage: String?
) {
    AnimatedVisibility(
        visible = !errorMessage.isNullOrBlank(),
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.error)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = errorMessage.orEmpty(),
                color = MaterialTheme.colorScheme.onError,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun PictureRowItem(
    modifier: Modifier = Modifier,
    data: Weather,
    onPictureClick: (Weather) -> Unit = {}
) {
    val entireTextDisplay = remember { mutableStateOf(false) }

    Row(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background)
        .clickable { onPictureClick(data) }
        .padding(16.dp)) {
        AsyncImage(
            model = data.icon,
            contentDescription = "une photo de chat",
            contentScale=ContentScale.Fit,
            error = painterResource(Res.drawable.error),
            onError = { println(it) },
            modifier = Modifier
                .heightIn(max = 100.dp)
                .widthIn(max = 100.dp)
        )

        Column(modifier = Modifier.clickable { entireTextDisplay.value = !entireTextDisplay.value}
            .animateContentSize()
            .padding(16.dp)) {
            Text(text = data.name,fontSize = 20.sp)

            val displayText = if (entireTextDisplay.value) {
                data.getResume()
            } else {
                data.getResume().take(20) + "..."
            }

            Text(text = displayText,fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, searchText:MutableState<String>) {
    TextField(
        value = searchText.value,
        onValueChange = {newValue:String -> searchText.value = newValue},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        singleLine = true,
        placeholder = {
            Text("Rechercher")
        },

        //keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search), // Définir le bouton "Entrée" comme action de recherche
        //keyboardActions = KeyboardActions(onSearch = {onSearchAction()}), // Déclenche l'action définie
        //Comment le composant doit se placer
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
expect fun WeatherGallery(modifier:Modifier = Modifier, list: List<Weather>, onPictureClick: (Weather)->Unit)
