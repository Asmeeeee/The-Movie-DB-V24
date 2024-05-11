package com.example.themoviedbv24.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.themoviedbv24.database.MovieDatabase
import com.example.themoviedbv24.database.NetworkCacheMoviesRepository
import com.example.themoviedbv24.utils.MovieListConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CacheWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val database = MovieDatabase.getDatabase(applicationContext)
        val movieCacheDao = database.cacheMoviesDao()
        val moviesRepository = NetworkCacheMoviesRepository(movieCacheDao)
        val movieListConverter = MovieListConverter();

        return withContext(Dispatchers.IO) {
            return@withContext try {
                val movieResponse = moviesRepository.getCacheMovies()
                val movieList = movieResponse.results
                movieCacheDao.clearCacheMovies()
                movieCacheDao.insertCacheMovies(movieResponse)
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}