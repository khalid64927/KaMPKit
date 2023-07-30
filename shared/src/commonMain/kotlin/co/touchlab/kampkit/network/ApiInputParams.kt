package co.touchlab.kampkit.network

import co.touchlab.kampkit.common.AppConfigs

/**
 * Generic class to override request parameters
 *  1. baseUrl if given will be used instead
 *  1. pathString endpoint to append onto baseUrl
 *  1. requestHeaders hashMap of request headers to add
 * */
data class ApiInputParams(
    val baseUrl: String? = AppConfigs.baseUrl,
    val pathString: String,
    var requestHeaders: Map<String, Any> = emptyMap()
){
    companion object{
        val GET_ALL_BREEDS = ApiInputParams(pathString = Endpoints.ALL_BREEDS)

    }
}

object InputParams {
    val GET_ALL_BREEDS = ApiInputParams(pathString = Endpoints.ALL_BREEDS)
}



