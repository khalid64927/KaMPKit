package co.touchlab.kampkit.ktor

import co.touchlab.kampkit.common.Result
import co.touchlab.kampkit.common.orBaseUrl
import co.touchlab.kampkit.network.ApiInputParams
import co.touchlab.kampkit.network.KtorAppClient
import co.touchlab.kampkit.network.safeGetRequest
import co.touchlab.kampkit.response.BreedResult
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import co.touchlab.kermit.Logger as KermitLogger

class AppApiInterfaceImpl(private val ktorAppClient: KtorAppClient, private val log: KermitLogger) : AppApiInterface {

    private val client by lazy { ktorAppClient.client }
    override suspend fun getDogBreeds(params: ApiInputParams): Result<BreedResult> {
        return client.safeGetRequest {
            url {
                takeFrom(params.baseUrl.orBaseUrl())
                encodedPath = params.pathString
            }
        }
    }
}
