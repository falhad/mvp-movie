package dev.falhad.movieshowcase.network

import dev.falhad.movieshowcase.model.api.GeneralException
import dev.falhad.movieshowcase.model.api.GeneralResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response


private fun ResponseBody?.parse(): GeneralException {
    try {
        this?.string()?.let {
            return Gson().fromJson(it, GeneralException::class.java)
        }
        return GeneralException("error", 500, "unknown error")
    } catch (e: Exception) {
        return GeneralException("exception", 501, e.localizedMessage ?: e.message ?: "unknown error")
    }
}

private val <T> Response<out T>?.generalException: GeneralException
    get() = this?.errorBody().parse()

fun <T> Response<out T>?.parse(): GeneralException {
    return this?.errorBody().parse()
}


