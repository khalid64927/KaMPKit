package co.touchlab.kampkit.ktor

import co.touchlab.kampkit.common.Result
import co.touchlab.kampkit.network.ApiInputParams
import co.touchlab.kampkit.response.BreedResult

interface AppApiInterface {
    suspend fun getDogBreeds(params: ApiInputParams): Result<BreedResult>
}
