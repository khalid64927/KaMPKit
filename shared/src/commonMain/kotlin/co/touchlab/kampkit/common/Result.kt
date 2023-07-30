package co.touchlab.kampkit.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


/**
 * This data model is used to share with Compose
 * */
sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
    object Loading : Result<Nothing>
}

inline fun <T: Any> Result<T>.onSuccess(action: (T) -> Unit) : Result<T> {
    if(this is Result.Success)
        action(data)
    return this
}

inline fun <T: Any> Result<T>.onFailure(action: (Throwable) -> Unit) : Result<T> {
    if(this is Result.Error && exception != null)
        // TODO: Do Error Handling
        action(exception)
    return this
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Result.Success(it)
        }
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Error(it)) }
}