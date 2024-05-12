package com.example.themoviedbv24.database

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.themoviedbv24.MovieDBScreen
import com.example.themoviedbv24.model.Movie
import com.example.themoviedbv24.workers.CacheWorker
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CacheWorkManagerRepository(context: Context) {
    private val workManager = WorkManager.getInstance(context)

    fun cacheMovieList(category: MovieDBScreen, movies: List<Movie>) {
        val inputData = workDataOf(
            CacheWorkManagerRepository.KEY_CATEGORY to category.name,
            CacheWorkManagerRepository.KEY_MOVIES to Json.encodeToString(movies)
        )

        val saveMovieListRequest = OneTimeWorkRequestBuilder<CacheWorker>()
            .addTag(CacheWorkManagerRepository.SAVE_CACHE)
            .setInputData(inputData)
            .build()

        workManager.enqueue(saveMovieListRequest)
    }

    fun cancelWork() {
        workManager.cancelUniqueWork(SAVE_CACHE)
    }

    companion object {
        private const val SAVE_CACHE = "save_in_cache"
        private const val KEY_CATEGORY = "category"
        private const val KEY_MOVIES = "movies"
    }
}