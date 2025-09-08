package com.softwarecleandevelopment.core.common.utils

import kotlinx.coroutines.flow.Flow

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
        val result = call()
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
    call: () -> Flow<T>
): Resource<Flow<T>> {
    return try {
        Resource.Loading
        val result = call()
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