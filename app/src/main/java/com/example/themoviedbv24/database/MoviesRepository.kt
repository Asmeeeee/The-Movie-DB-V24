package com.example.themoviedbv24.database

import com.example.themoviedbv24.model.Movie
import com.example.themoviedbv24.model.MovieDetail
import com.example.themoviedbv24.model.MovieResponse
import com.example.themoviedbv24.model.MovieReviewResponse
import com.example.themoviedbv24.model.MovieReviews
import com.example.themoviedbv24.model.MovieVideo
import com.example.themoviedbv24.model.MovieVideoResponse
import com.example.themoviedbv24.network.MovieDBApiService


interface MoviesRepository {
    suspend fun getPopularMovies(): MovieResponse
    suspend fun getTopRatedMovies(): MovieResponse
    suspend fun getMovieDetail(id: Long): MovieDetail
    suspend fun getMovieReviews(id: Long): MovieReviewResponse
    suspend fun getMovieVideos(id: Long): MovieVideoResponse
    suspend fun getMovie(id: Long): Movie
}

class NetworkMoviesRepository(private val apiService: MovieDBApiService) : MoviesRepository {
    override suspend fun getPopularMovies(): MovieResponse {
        return apiService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): MovieResponse {
        return apiService.getTopRatedMovies()
    }

    override suspend fun getMovieDetail(id: Long): MovieDetail {
        return apiService.getMovieDetail(id);
    }

    override suspend fun getMovie(id: Long): Movie {
        return apiService.getMovie(id);
    }

    override suspend fun getMovieReviews(id: Long): MovieReviewResponse {
        return apiService.getMovieReviews(id);
    }

    override suspend fun getMovieVideos(id: Long): MovieVideoResponse {
        return apiService.getMovieVideos(id);
    }
}

interface CacheMoviesRepository {
    suspend fun getCacheMovies(): MovieResponse
    suspend fun insertCacheMovies(movie: MovieResponse)
    suspend fun clearCacheMovies()
}

class NetworkCacheMoviesRepository(private val cacheMoviesDao: CacheMoviesDao) : CacheMoviesDao{
    override suspend fun getCacheMovies(): MovieResponse {
        return cacheMoviesDao.getCacheMovies();
    }

    override suspend fun insertCacheMovies(movie: MovieResponse) {
        cacheMoviesDao.insertCacheMovies(movie)
    }

    override suspend fun clearCacheMovies() {
        cacheMoviesDao.clearCacheMovies();
    }
}

interface SavedMovieRepository{
    suspend fun getSavedMovies(): List<Movie>
    suspend fun inserMovie(movie: Movie)
    suspend fun getMovie(id: Long): Movie
    suspend fun deleteMovie(id: Long)
}

class FavoriteMovieRepository(private val movieDao: MovieDao) : SavedMovieRepository {
    override suspend fun getSavedMovies(): List<Movie> {
        return movieDao.getFavoriteMovies()
    }

    override suspend fun inserMovie(movie: Movie) {
        movieDao.insertFavoriteMovie(movie)
    }

    override suspend fun getMovie(id: Long): Movie {
        return movieDao.getMovie(id)
    }

    override suspend fun deleteMovie(id: Long) {
        movieDao.deleteFavoriteMovie(id)
    }
}