package dev.falhad.movieshowcase.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import dev.falhad.movieshowcase.model.db.entity.SettingEntity

@Dao
interface SettingDao {

    @Query("SELECT * FROM settings")
    fun getAll(): LiveData<List<SettingEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg settings: SettingEntity)

    @Delete
    suspend fun delete(settings: SettingEntity)

    @Query("DELETE FROM settings")
    suspend fun deleteAll()


}