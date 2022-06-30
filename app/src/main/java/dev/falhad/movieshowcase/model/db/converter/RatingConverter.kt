package dev.falhad.movieshowcase.model.db.converter

import android.media.Rating
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

public class RatingConverter {

    private val gson by lazy { Gson() }
    private val type = object : TypeToken<List<Rating>>() {}.type

    @TypeConverter
    fun toRating(rating: String?): List<Rating>? {
        return gson.fromJson(rating, type)
    }

    @TypeConverter
    fun toJson(rating: Rating): String? {
        return gson.toJson(rating)
    }
}