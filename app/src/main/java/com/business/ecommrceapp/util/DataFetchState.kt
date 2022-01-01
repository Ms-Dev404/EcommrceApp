package com.business.ecommrceapp.util

sealed class DataFetchState<out T>{

  data class Loading(val info: Info): DataFetchState<Nothing>()
  data class Success<T>(val info: Info, val responseData:T): DataFetchState<T>()
  data class Failure(val info: Info, val e:Exception): DataFetchState<Nothing>()
}
