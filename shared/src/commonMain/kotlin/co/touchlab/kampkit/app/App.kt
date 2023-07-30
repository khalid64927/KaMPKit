package co.touchlab.kampkit.app

import androidx.compose.runtime.Composable
import co.touchlab.kampkit.common.ui.theme.KaMPKitTheme
import co.touchlab.kampkit.features.doglist.MainScreen
import co.touchlab.kampkit.models.BreedViewModel
import co.touchlab.kermit.Logger
import org.koin.compose.koinInject

@Composable
fun App() {
    val breedViewModel: BreedViewModel = koinInject()
    val log: Logger = koinInject()
    KaMPKitTheme {
        MainScreen(breedViewModel, log)
    }

}