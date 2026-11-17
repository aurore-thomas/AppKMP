package com.example.appkmp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appkmp.shared.generated.resources.Res
import appkmp.shared.generated.resources.error
import coil3.compose.AsyncImage
import com.example.appkmp.domain.model.Weather
import com.example.appkmp.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource

@Preview(showBackground = true, showSystemUi = true, locale = "fr",
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun DetailScreenPreview() {
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DetailScreen(
                modifier = Modifier.padding(innerPadding),
                //jeu de données pour la Preview
                data = Weather(
                    id = 1,
                    name = "Nice",
                    temp = 18.5,
                    description = "ciel dégagé",
                    icon = "https://picsum.photos/200",
                    speed = 5.0
                )
            )
        }
    }
}
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    data: Weather,
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = data.name,
            fontSize = 32.sp,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        AsyncImage(
            model = data.icon,
            contentDescription = "Icône météo pour ${data.name}",
            contentScale = ContentScale.Fit,
            error = painterResource(Res.drawable.error),
            onError = { println(it) },
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = data.getResume(),
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onBackClick,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Retour"
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text("Retour")
        }
    }
}
