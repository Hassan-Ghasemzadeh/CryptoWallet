package com.softwarecleandevelopment.core.common.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

suspend fun <T> safeCall(
    call: suspend () -> T
): Resource<T> {
    return try {
        Resource.Loading
        val result = withContext(Dispatchers.IO) { call() }
        Resource.Success(result)
    } catch (e: Exception) {
        e.printStackTrace()
        val errorMessage = when (e) {
            is java.net.SocketTimeoutException -> "Connection timed out. Please try again."
            is java.net.UnknownHostException -> "No internet connection. Please check your network."
            else -> "An unexpected error occurred."
        }
        Resource.Error(errorMessage, e)
    }
}

fun <T> safeFlowCall(
    // Input is a suspending function to fetch the data
    call: suspend () -> T
): Flow<Resource<T>> = flow {
    // Emit Loading state immediately
    emit(Resource.Loading)

    try {
        //Call the suspending function (often a Repository or DataSource call)
        val result = call()

        // Emit Success state with the data
        emit(Resource.Success(result))

    } catch (e: Exception) {
        e.printStackTrace()

        // Handle exceptions and emit Error state
        val errorMessage = when (e) {
            is SocketTimeoutException -> "Connection timed out. Please try again."
            is UnknownHostException -> "No internet connection. Please check your network."
            else -> "An unexpected error occurred."
        }
        emit(Resource.Error(errorMessage, e))
    }
}