package com.example.appkmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

import appkmp.shared.generated.resources.Res
import appkmp.shared.generated.resources.compose_multiplatform
import appkmp.shared.generated.resources.error
import appkmp.shared.generated.resources.string
import com.example.appkmp.di.apiModule
import com.example.appkmp.di.viewModelModule
import com.example.appkmp.ui.theme.AppTheme
import com.example.appkmp.viewmodel.MainViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(apiModule, viewModelModule)
    }) {
        AppTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                AppNavigation(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }


}

