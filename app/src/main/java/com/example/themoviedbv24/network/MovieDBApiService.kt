package com.example.themoviedbv24.network

import com.example.themoviedbv24.model.Movie
import com.example.themoviedbv24.model.MovieDetail
import com.example.themoviedbv24.model.MovieResponse
import com.example.themoviedbv24.model.MovieReviewResponse
import com.example.themoviedbv24.model.MovieReviews
import com.example.themoviedbv24.model.MovieVideo
import com.example.themoviedbv24.model.MovieVideoResponse
import com.example.themoviedbv24.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApiService {

    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieResponse
    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieResponse

    @GET("{id}")
    suspend fun getMovieDetail(
        @Path("id") movieId: Long,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): MovieDetail

    @GET("{id}")
    suspend fun getMovie(
        @Path("id") movieId: Long,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Movie

    @GET("{id}/reviews")
    suspend fun getMovieReviews(
        @Path("id") movieId: Long,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): MovieReviewResponse

    @GET("{id}/videos")
    suspend fun getMovieVideos(
        @Path("id") movieId: Long,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): MovieVideoResponse
}