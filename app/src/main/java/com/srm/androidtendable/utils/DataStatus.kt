package com.srm.androidtendable.utils

data class DataStatus<out T>(val status: Status, val data: T? = null, val message: String? = null) {

    enum class Status {
        LOADING, SUCCESS, ERROR
    }

    companion object {
        fun <T> loading(): DataStatus<T> {
            return DataStatus(Status.LOADING)
        }

        fun <T> success(data: T?): DataStatus<T> {
            return DataStatus(Status.SUCCESS, data)
        }

        fun <T> error(error: String): DataStatus<T> {
            return DataStatus(Status.ERROR, message = error)
        }

    }
}

sealed class UIState {
    data object Loading : UIState()

    data class Success(val data: Int) : UIState()

    data object Error : UIState()

    data object AlreadyExists : UIState()
}
