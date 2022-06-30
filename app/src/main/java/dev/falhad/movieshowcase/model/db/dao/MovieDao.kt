package dev.falhad.movieshowcase.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import dev.falhad.movieshowcase.model.db.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): LiveData<List<MovieEntity>>



    @Query("SELECT * FROM movies where Favorite = 1 and CanShow = 1")
    fun favorites(): LiveData<List<MovieEntity>>

     @Query("SELECT * FROM movies where CanShow = 0")
    fun hideList(): LiveData<List<MovieEntity>>



    @Query("SELECT * FROM movies where imdbID = :imdbId")
    suspend fun getByIMDBId(imdbId: String): MovieEntity?

    @Query("SELECT * FROM movies where imdbID = :imdbId")
    fun liveByIMDBId(imdbId: String): LiveData<MovieEntity?>



    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg movies: MovieEntity)

    @Delete
    suspend fun delete(movies: MovieEntity)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()


}