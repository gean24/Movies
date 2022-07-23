package com.mauro.reto.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mauro.reto.storage.entity.MovieDb
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert
    fun insertMovie(movies: List<MovieDb>):List<Long>

    @Query("SELECT * FROM movies WHERE page = :page")
    fun getMovies(page: Int): Flow<List<MovieDb>>

    @Query("SELECT * FROM movies WHERE localId = :localId")
    fun getMovieById(localId: Int): Flow<MovieDb>

    @Query("DELETE FROM movies")
    fun clearMovie()

    @Transaction
    fun clearAndInsertMovie(movie: List<MovieDb>, isFirst: Boolean) {
        if(isFirst) {
            clearMovie()
        }
        insertMovie(movie)
    }
}