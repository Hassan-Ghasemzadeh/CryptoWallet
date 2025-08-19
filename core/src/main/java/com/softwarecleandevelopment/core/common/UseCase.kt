package com.softwarecleandevelopment.core.common

interface UseCase<out T, in P> {
    suspend operator fun invoke(params: P): T
}