package com.andrefpc.data

sealed class ApiResult<out T> {
    class Error(val error: String?) : ApiResult<Empty>()
    class Success<T>(val result: T?) : ApiResult<T>()
}
