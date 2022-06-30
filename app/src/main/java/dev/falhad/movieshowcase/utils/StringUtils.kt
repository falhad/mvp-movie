package dev.falhad.movieshowcase.utils

import java.lang.Exception
import java.util.*


fun <E> List<E>.toArrayList(): ArrayList<E> {
    val arrayListOf = arrayListOf<E>()
    arrayListOf.addAll(this)
    return arrayListOf
}


inline fun <reified T : Enum<T>> enumValueOfOrNull(type: String): T? {
    return try {
        java.lang.Enum.valueOf(T::class.java, type)
    } catch (e: IllegalArgumentException) {
        null
    }
}

inline fun <reified T : Enum<T>> enumValueOfOrDefault(type: String, default: T): T {
    return try {
        java.lang.Enum.valueOf(T::class.java, type)
    } catch (e: IllegalArgumentException) {
        default
    }
}


