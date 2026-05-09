package com.example.githubtestapp.data.remote.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ApiCallHandler {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(apiCall())
            } catch (exception: HttpException) {
                Result.failure(
                    Throwable("Server error: ${exception.code()}")
                )
            } catch (_: IOException) {
                Result.failure(
                    Throwable("Network error. Check your internet connection.")
                )
            } catch (exception: Exception) {
                Result.failure(
                    Throwable(exception.message ?: "Unexpected error occurred.")
                )
            }
        }
    }
}