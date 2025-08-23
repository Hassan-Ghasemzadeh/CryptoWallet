package com.softwarecleandevelopment.core.common

abstract class UseCase<out T, in P> {
    abstract suspend operator fun invoke(params: P): T
}