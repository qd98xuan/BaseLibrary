package com.hx.baselibrary.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  Retrofit+Flow 请求封装
 */
class RetrofitFlowWrapper private constructor() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    companion object {
        private const val BASE_URL = ""
        private var instance: RetrofitFlowWrapper? = null

        @Synchronized
        fun getInstance(): RetrofitFlowWrapper {
            if (instance == null) {
                instance = RetrofitFlowWrapper()
            }
            return instance!!
        }
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    suspend fun <T> makeApiRequest(flow: Flow<Response<BaseResponse<T>>>) = flow {
        emit(Result.Loading)
        try {
            val response = flow.first()
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.code == 200) {
                    emit(Result.Success(body?.data))
                } else {
                    emit(Result.Error(body?.message ?: ""))
                }
            } else {
                emit(Result.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: ""))
        }
    }
}

data class BaseResponse<T>(
    val code: Int,
    val data: T,
    val message: String
)

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val msg: String) : Result<Nothing>()
}