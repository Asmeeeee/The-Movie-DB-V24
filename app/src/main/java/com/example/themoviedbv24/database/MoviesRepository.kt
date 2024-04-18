package com.example.themoviedbv24.database

import com.example.themoviedbv24.model.MovieDetail
import com.example.themoviedbv24.model.MovieResponse
import com.example.themoviedbv24.network.MovieDBApiService


interface MoviesRepository {
    suspend fun getPopularMovies(): MovieResponse
    suspend fun getTopRatedMovies(): MovieResponse

    suspend fun getMovieDetail(id: Long): MovieDetail
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
}