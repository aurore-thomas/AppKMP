package com.example.appkmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.appkmp.Routes.SearchRoute
import com.example.appkmp.domain.model.Weather
import com.example.appkmp.ui.screens.DetailScreen
import com.example.appkmp.ui.screens.SearchScreen
import com.example.appkmp.viewmodel.MainViewModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

class Routes {
    @Serializable
    data object SearchRoute : NavKey

    //Les paramètres peuvent être des objets, à condition qu'ils soient @Serializable
    @Serializable
    data class DetailRoute(val weather: Weather) : NavKey
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    //La pile d'écrans, avec l'écran de départ. Sauvegardée lors des rotations
    val backStack = rememberNavBackStack(navConfig, Routes.SearchRoute)
    val mainViewModel: MainViewModel = koinViewModel<MainViewModel>()

    LaunchedEffect(Unit) {
        mainViewModel.loadWeathers("")
    }

    NavDisplay(
        backStack = backStack,
        //Retour système (bouton ou geste) : on retire le dernier écran
        onBack = { backStack.removeLastOrNull() },
        modifier = modifier,
        entryProvider = entryProvider {
            //Route 1 vers notre SearchScreen
            entry<SearchRoute> {
                SearchScreen(
                    mainViewModel = mainViewModel,
                    //On remonte l'événement : la navigation reste dans Navigation.kt
                    onPictureClick = { backStack.add(Routes.DetailRoute(it)) }
                )
            }

            //Route 2 vers un écran de détail
            //La route est reçue directement, avec ses paramètres
            entry<Routes.DetailRoute> { detailRoute ->
                DetailScreen(data = detailRoute.weather,
                    onBackClick = { backStack.removeLastOrNull() }
                )
            }
        }
    )

}

private val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Routes.SearchRoute::class, Routes.SearchRoute.serializer())
            subclass(Routes.DetailRoute::class, Routes.DetailRoute.serializer())
        }
    }
}