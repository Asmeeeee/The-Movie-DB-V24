package com.example.themoviedbv24.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.themoviedbv24.MovieDBApplication
import com.example.themoviedbv24.database.FavoriteMovieRepository
import com.example.themoviedbv24.database.MoviesRepository
import com.example.themoviedbv24.database.SavedMovieRepository
import com.example.themoviedbv24.model.Movie
import com.example.themoviedbv24.model.MovieDetail
import com.example.themoviedbv24.model.MovieReviews
import com.example.themoviedbv24.model.MovieVideo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MovieListUiState {
    data class Success(val movies: List<Movie>) : MovieListUiState
    object Error : MovieListUiState
    object Loading : MovieListUiState
}

sealed interface SelectedMovieUiState {
    data class Success(val movieDetail: MovieDetail, val is_Favorite: Boolean) : SelectedMovieUiState
    object Error : SelectedMovieUiState
    object Loading : SelectedMovieUiState
}

sealed interface SelectedMovieReviewsUiState {
    data class Success(val movieReviewsList: List<MovieReviews>) : SelectedMovieReviewsUiState
    object Error : SelectedMovieReviewsUiState
    object Loading : SelectedMovieReviewsUiState
}

sealed interface SelectedMovieVideosUiState {
    data class Success(val movieVideosList: List<MovieVideo>) : SelectedMovieVideosUiState
    object Error : SelectedMovieVideosUiState
    object Loading : SelectedMovieVideosUiState
}


class MovieDBViewModel(private val moviesRepository: MoviesRepository, private val savedMovieRepository: SavedMovieRepository) : ViewModel() {

    var movieListUiState: MovieListUiState by mutableStateOf(MovieListUiState.Loading)
        private set

    var selectedMovieUiState: SelectedMovieUiState by mutableStateOf(SelectedMovieUiState.Loading)
        private set

    var selectedMovieReviewsUiState: SelectedMovieReviewsUiState by mutableStateOf(SelectedMovieReviewsUiState.Loading)
        private set

    var selectedMovieVideosUiState: SelectedMovieVideosUiState by mutableStateOf(SelectedMovieVideosUiState.Loading)
        private set

    init {
        getPopularMovies()
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(moviesRepository.getTopRatedMovies().results)
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(moviesRepository.getPopularMovies().results)
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun setSelectedMovieDetail(idMovie: Long) {
        viewModelScope.launch {
            selectedMovieUiState = SelectedMovieUiState.Loading
            selectedMovieUiState = try {
                SelectedMovieUiState.Success(moviesRepository.getMovieDetail(idMovie), savedMovieRepository.getMovie(idMovie) != null)
            } catch (e: IOException) {
                SelectedMovieUiState.Error
            } catch (e: HttpException) {
                SelectedMovieUiState.Error
            }
        }
    }

    fun setSelectedMovieExtraUiState(idMovie: Long) {
        viewModelScope.launch {
            selectedMovieReviewsUiState = SelectedMovieReviewsUiState.Loading
            selectedMovieReviewsUiState = try {
                SelectedMovieReviewsUiState.Success(moviesRepository.getMovieReviews(idMovie).results)
            } catch (e: IOException) {
                SelectedMovieReviewsUiState.Error
            } catch (e: HttpException) {
                SelectedMovieReviewsUiState.Error
            }
        }
    }

    fun setSelectedMovieVideosUiState(idMovie: Long) {
        viewModelScope.launch {
            selectedMovieVideosUiState = SelectedMovieVideosUiState.Loading
            selectedMovieVideosUiState = try {
                SelectedMovieVideosUiState.Success(moviesRepository.getMovieVideos(idMovie).results)
            } catch (e: IOException) {
                SelectedMovieVideosUiState.Error
            } catch (e: HttpException) {
                SelectedMovieVideosUiState.Error
            }
        }
    }

    fun getSavedMovies(){
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(savedMovieRepository.getSavedMovies())
            } catch (e: IOException) {
                MovieListUiState.Error
            }
             catch (e: HttpException) {
                 MovieListUiState.Error
             }
        }
    }

    fun saveMovie(movie: Movie){
        viewModelScope.launch {
            savedMovieRepository.inserMovie(movie)
            //selectedMovieUiState = SelectedMovieUiState.Success(movie, true)
        }
    }

    fun deleteMovie(movie: Movie){
        viewModelScope.launch {
            savedMovieRepository.deleteMovie(movie.id)
            //selectedMovieUiState = SelectedMovieUiState.Success(movieDetail, false)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieDBApplication)
                val moviesRepository = application.container.moviesRepository
                val savedMovieRepository = application.container.savedMovieRepository
                MovieDBViewModel(moviesRepository = moviesRepository, savedMovieRepository = savedMovieRepository)
            }
        }
    }
}