package com.solvro.topwr.core.api

/**
 * Wrapper for data to handle it in a convenient way
 * Three status:
 * 1. SUCCES -> data is available - everything's OKEY
 * 2. LOADING -> request isn't finished
 * 3. ERROR -> Some error happened
 */
sealed class Resource<T>(open val data: T?) {

    data class Success<T>(
        override val data: T
    ) : Resource<T>(data)

    data class Error<T>(val message: String, override val data: T? = null) : Resource<T>(data)

    data class Loading<T>(override val data: T? = null) : Resource<T>(data)

    fun <S> map(transform: (T) -> S): Resource<S> {
        return when (this) {
            is Success -> Success(transform.invoke(data))
            is Error -> Error(message, data?.let { transform.invoke(it) })
            is Loading -> Loading(data?.let { transform.invoke(it) })
        }
    }
}