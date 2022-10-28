package com.solvro.topwr.utils

/**
 * Wrapper for data to handle it in a convenient way
 * Three status:
 * 1. SUCCES -> data is available - everything's OKEY
 * 2. LOADING -> request isn't finished
 * 3. ERROR -> Some error happened
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

    fun <S> map(transform: (T) -> S): Resource<S> {
        return when (this.status) {
            Status.SUCCESS -> success(transform.invoke(data!!))
            Status.ERROR -> error(message!!, data?.let { transform.invoke(it) })
            Status.LOADING -> loading(data?.let { transform.invoke(it) })
        }
    }
}