package dev.falhad.movieshowcase.network

import androidx.annotation.StringRes

sealed class Presenter<out T> {
    object Loading : Presenter<Nothing>() {
        override fun toString(): String {
            return "loading.."
        }
    }

    data class Error(
        val msg: String? = null,
        @StringRes val res: Int? = null,
        val code: Int? = null
    ) :
        Presenter<Nothing>() {
        override fun toString(): String {
            return "error: [$code] msg:$msg, res:$res"
        }
    }

    data class Success<T>(val item: T) : Presenter<T>() {
        override fun toString(): String {
            return item.toString()
        }
    }


}