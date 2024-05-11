package com.example.themoviedbv24.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themoviedbv24.model.Movie
import com.example.themoviedbv24.model.MovieResponse

@Dao
interface CacheMoviesDao {

    @Query("SELECT * FROM cache_movies")
    suspend fun getCacheMovies(): MovieResponse

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCacheMovies(movie: MovieResponse)

    @Query("DELETE FROM cache_movies")
    suspend fun clearCacheMovies()
}