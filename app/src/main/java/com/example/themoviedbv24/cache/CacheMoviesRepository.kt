package com.example.themoviedbv24.cache

import com.example.themoviedbv24.database.CacheMoviesDao
import com.example.themoviedbv24.model.MovieCache
import com.example.themoviedbv24.model.MovieResponse

interface CacheMoviesRepository {
    suspend fun getCacheMovies(): MovieCache
    suspend fun insertCacheMovies(movie: MovieCache)
    suspend fun clearCacheMovies()
    suspend fun getCategory(): String
}

class NetworkCacheMoviesRepository(private val cacheMoviesDao: CacheMoviesDao) : CacheMoviesRepository {
    override suspend fun getCategory(): String {
        return cacheMoviesDao.getCategory();
    }

    override suspend fun getCacheMovies(): MovieCache {
        return cacheMoviesDao.getCacheMovies();
    }

    override suspend fun insertCacheMovies(movie: MovieCache) {
        cacheMoviesDao.insertCacheMovies(movie)
    }

    override suspend fun clearCacheMovies() {
        cacheMoviesDao.clearCacheMovies();
    }
}