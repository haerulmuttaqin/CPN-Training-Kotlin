package id.haerulmuttaqin.entity

sealed class ResultState<out T: Any> {
    data class Success<out T: Any>(val data: T): ResultState<T>()
    data class Error(val throwable: Throwable): ResultState<Nothing>()
    object Empty: ResultState<Nothing>()
}
