package dev.falhad.movieshowcase.model.api

data class GeneralResponse(
    val status: String? = null,
    val code: Int? = null,
    val message: String? = null
)

data class GeneralException(
    val status: String? = null,
    val code: Int? = null,
    override val message: String? = null
) : Exception()