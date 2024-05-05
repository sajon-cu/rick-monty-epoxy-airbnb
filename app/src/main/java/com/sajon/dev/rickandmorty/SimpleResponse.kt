package com.sajon.dev.rickandmorty

import retrofit2.Response

data class SimpleResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val error: Exception?
) {
    companion object {
        fun <T> success(data: Response<T>): SimpleResponse<T> {
            return SimpleResponse(
                Status.Success,
                data,
                null
            )
        }

        fun <T> failure(error: Exception): SimpleResponse<T> {
            return SimpleResponse(
                Status.Failure,
                null,
                error
            )
        }
    }

    sealed class Status {
        data object Success: Status()
        data object Failure: Status()
    }

    val isFailed: Boolean
        get() = this.status == Status.Failure

    val isSuccessful: Boolean
        get() = !isFailed && this.data?.isSuccessful == true

    val body: T
        get() = this.data?.body()!!
}