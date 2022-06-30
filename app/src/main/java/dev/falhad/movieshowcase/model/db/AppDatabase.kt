package dev.falhad.movieshowcase.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.falhad.movieshowcase.model.db.converter.RatingConverter
import dev.falhad.movieshowcase.model.db.dao.MovieDao
import dev.falhad.movieshowcase.model.db.dao.SettingDao
import dev.falhad.movieshowcase.model.db.entity.MovieEntity
import dev.falhad.movieshowcase.model.db.entity.SettingEntity

@Database(
    entities = [SettingEntity::class, MovieEntity::class],
    version = 4
)
@TypeConverters(RatingConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun settingDao(): SettingDao
    abstract fun movieDao(): MovieDao
}