package com.softwarecleandevelopment.core.common.utils

import kotlinx.coroutines.flow.Flow

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Resource<Nothing>()
}

suspend fun <T> safeCall(
    call: suspend () -> T
): Resource<T> {
    return try {
        val result = call()
        Resource.Success(result)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Error in ${call()} function", e)
    }
}

fun <T> safeFlowCall(
    call: () -> Flow<T>
): Resource<Flow<T>> {
    return try {
        val result = call()
        Resource.Success(result)
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Error in ${call()} function", e)
    }
}