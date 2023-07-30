package co.touchlab.kampkit.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

sealed class ApiResponse<out T> {
    /**
     * Represents successful network responses (2xx).
     */
    data class Success<T>(val body: T) : ApiResponse<T>()

    sealed class Error : ApiResponse<Nothing>() {
        /**
         * Represents server (50x) and client (40x) errors.
         */
        data class HttpError(val code: Int, val errorBody: String?) : Error()

        /**
         * Represent IOExceptions and connectivity issues.
         */
        object NetworkError : Error()

        /**
         * Represent SerializationExceptions.
         */
        object SerializationError : Error()

        /**
         * Represent any other errors.
         */
        object Unknown : Error()
    }
}
