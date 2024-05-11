package com.example.themoviedbv24.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.themoviedbv24.model.Movie
import com.example.themoviedbv24.model.MovieListConverter
import com.example.themoviedbv24.model.MovieResponse

@Database(entities = [Movie::class, MovieResponse::class], version = 2, exportSchema = false)
@TypeConverters(MovieListConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract  fun movieDao(): MovieDao
    abstract fun cacheMoviesDao(): CacheMoviesDao

    companion object{
        @Volatile
        private var Instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance= it }
            }
        }
    }
}