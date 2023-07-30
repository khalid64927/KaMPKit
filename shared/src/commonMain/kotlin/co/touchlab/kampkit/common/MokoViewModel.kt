package co.touchlab.kampkit.common

import dev.icerock.moko.mvvm.viewmodel.ViewModel

open class MokoViewModel: ViewModel()

data class MokoState(
    val message: String)