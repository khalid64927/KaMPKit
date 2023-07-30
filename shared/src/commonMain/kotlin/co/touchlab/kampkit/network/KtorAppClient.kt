package co.touchlab.kampkit.network


import co.touchlab.kermit.Logger as KermitLogger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.logging.Logger as KtorLogger
import co.touchlab.kampkit.common.Result

class KtorAppClient(private val log: KermitLogger, engine: HttpClientEngine) {
    
    open val logger: KermitLogger
        get() = log

    val client = HttpClient(engine) {
        expectSuccess = true
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            logger = object : KtorLogger {
                override fun log(message: String) {
                    log.v { message }
                }
            }
            level = LogLevel.INFO
        }
        install(HttpTimeout) {
            val timeout = 30000L
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
    }
}


suspend inline fun <reified T> HttpClient.safeGetRequest(
    block: HttpRequestBuilder.() -> Unit,
): Result<T> =
    try {
        val response = get { block() }
        Result.Success(response.body())
    } catch (e: Exception){
        Result.Error(e)
    }

suspend inline fun <reified T> HttpClient.safePostRequest(
    block: HttpRequestBuilder.() -> Unit,
): Result<T> =
    try {
        val response = post{ block() }
        Result.Success(response.body())
    } catch (e: Exception){
        Result.Error(e)
    }

suspend inline fun <reified T> HttpClient.safePutRequest(
    block: HttpRequestBuilder.() -> Unit,
): Result<T> =
    try {
        val response = put{ block() }
        Result.Success(response.body())
    } catch (e: Exception){
        Result.Error(e)
    }

suspend inline fun <reified T> HttpClient.safeDeleteRequest(
    block: HttpRequestBuilder.() -> Unit,
): Result<T> =
    try {
        val response = delete{ block() }
        Result.Success(response.body())
    } catch (e: Exception){
        Result.Error(e)
    }