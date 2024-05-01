package com.example.themoviedbv24.database

import com.example.themoviedbv24.model.Movie
import com.example.themoviedbv24.model.MovieDetail
import com.example.themoviedbv24.model.MovieResponse
import com.example.themoviedbv24.model.MovieReviews
import com.example.themoviedbv24.model.MovieVideo
import com.example.themoviedbv24.network.MovieDBApiService


interface MoviesRepository {
    suspend fun getPopularMovies(): MovieResponse<Movie>
    suspend fun getTopRatedMovies(): MovieResponse<Movie>
    suspend fun getMovieDetail(id: Long): MovieDetail
    suspend fun getMovieReviews(id: Long): MovieResponse<MovieReviews>
    suspend fun getMovieVideos(id: Long): MovieResponse<MovieVideo>
    suspend fun getMovie(id: Long): Movie
}

class NetworkMoviesRepository(private val apiService: MovieDBApiService) : MoviesRepository {
    override suspend fun getPopularMovies(): MovieResponse<Movie> {
        return apiService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): MovieResponse<Movie> {
        return apiService.getTopRatedMovies()
    }

    override suspend fun getMovieDetail(id: Long): MovieDetail {
        return apiService.getMovieDetail(id);
    }

    override suspend fun getMovie(id: Long): Movie {
        return apiService.getMovie(id);
    }

    override suspend fun getMovieReviews(id: Long): MovieResponse<MovieReviews> {
        return apiService.getMovieReviews(id);
    }

    override suspend fun getMovieVideos(id: Long): MovieResponse<MovieVideo> {
        return apiService.getMovieVideos(id);
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