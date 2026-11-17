package com.example.appkmp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.appkmp.data.remote.WeatherApiDataSource
import com.example.appkmp.data.fake.WeatherFakeDataSource
import com.example.appkmp.domain.model.Weather
import com.example.appkmp.ui.MyError
import com.example.appkmp.ui.PictureRowItem
import com.example.appkmp.ui.SearchBar
import com.example.appkmp.ui.WeatherGallery
import com.example.appkmp.ui.theme.AppTheme
import com.example.appkmp.viewmodel.MainViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    onPictureClick: (Weather) -> Unit = {}
) {
    val list =  mainViewModel.dataList.collectAsStateWithLifecycle().value
    val runInProgress = mainViewModel.runInProgress.collectAsStateWithLifecycle().value
    SearchScreenContent(
        modifier = modifier,
        list = list,
        onBtLoadClick = { mainViewModel.loadWeathers(it) },
        runInProgress,
        onPictureClick = onPictureClick
    )
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    list: List<Weather>,
    onBtLoadClick: (String) -> Unit = {},
    runInProgress: Boolean = false,
    errorMessage: String? = null,
    onPictureClick: (Weather) -> Unit = {}
) {
    val searchText : MutableState<String> = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.inversePrimary),
        horizontalAlignment = CenterHorizontally
    ) {
        SearchBar(searchText = searchText)
        MyError(errorMessage = errorMessage)

        AnimatedVisibility(visible = runInProgress) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }

        WeatherGallery(modifier, list, onPictureClick)

        Row {
            Button(
                onClick = { searchText.value = ""  },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Clear filter")
            }

            Button(
                onClick = { onBtLoadClick(searchText.value) },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Load data")
            }
        }
    }
}

@Preview(name = "Avec données", showBackground = true, showSystemUi = true)
@Preview(name = "Avec données sombre", showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenWithDataPreview() {
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreenContent(
                modifier = Modifier.padding(innerPadding),
                list = WeatherFakeDataSource.loadFakeData(),
                onBtLoadClick = {}
            )
        }
    }
}

@Preview(name = "Sans données", showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenEmptyPreview() {
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreenContent(
                modifier = Modifier.padding(innerPadding),
                list = emptyList(),
                onBtLoadClick = {},
                errorMessage = "Impossible de contacter le serveur météo."
            )
        }
    }
}
