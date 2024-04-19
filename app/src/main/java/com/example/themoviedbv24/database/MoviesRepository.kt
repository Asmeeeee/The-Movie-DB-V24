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

    override suspend fun getMovieReviews(id: Long): MovieResponse<MovieReviews> {
        return apiService.getMovieReviews(id);
    }

    override suspend fun getMovieVideos(id: Long): MovieResponse<MovieVideo> {
        return apiService.getMovieVideos(id);
    }
}