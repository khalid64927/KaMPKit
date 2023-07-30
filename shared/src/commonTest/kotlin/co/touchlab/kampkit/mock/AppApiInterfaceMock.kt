package co.touchlab.kampkit.mock

import co.touchlab.kampkit.common.Result
import co.touchlab.kampkit.ktor.AppApiInterface
import co.touchlab.kampkit.network.ApiInputParams
import co.touchlab.kampkit.response.BreedResult

// TODO convert this to use Ktor's MockEngine
class AppApiInterfaceMock : AppApiInterface {
    private var nextResult: () -> BreedResult = { error("Uninitialized!") }
    var calledCount = 0
        private set

    // override suspend fun getJsonFromApi(): BreedResult {
    //     val result = nextResult()
    //     calledCount++
    //     return result
    // }

    fun successResult(): BreedResult {
        val map = HashMap<String, List<String>>().apply {
            put("appenzeller", emptyList())
            put("australian", listOf("shepherd"))
        }
        return BreedResult(map, "success")
    }

    fun prepareResult(breedResult: BreedResult) {
        nextResult = { breedResult }
    }

    fun throwOnCall(throwable: Throwable) {
        nextResult = { throw throwable }
    }

    override suspend fun getDogBreeds(params: ApiInputParams): Result<BreedResult> {
        TODO("Not yet implemented")
    }
}
