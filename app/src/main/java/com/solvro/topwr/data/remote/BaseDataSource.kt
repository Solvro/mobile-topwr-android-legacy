package com.solvro.topwr.data.remote

import android.util.Log
import com.solvro.topwr.utils.Resource
import retrofit2.Response

/**
 * Base abstract class for DataSource
 */
abstract class BaseDataSource {

    /**
     * Function let you handle Retrofit response and wrap it into #Resource
     */
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        Log.d("BaseDataSource", call.toString())

        try {
            Log.d("BaseDataSource", "Block try/catch started")
            val response = call.invoke()
            Log.d("BaseDataSource", "Success?:" + response.isSuccessful.toString())
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("BaseDataSource", response.body().toString())
                    return Resource.success(body)
                }
            }else  Log.d("BaseDataSource", "Else")

            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network call has failed for a following reason: $message")
    }

}
