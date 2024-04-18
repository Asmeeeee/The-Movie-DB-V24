package com.example.themoviedbv24.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.themoviedbv24.model.Movie
import com.example.themoviedbv24.utils.Constants
import com.example.themoviedbv24.viewmodel.MovieListUiState

@Composable
fun MovieGridLayoutScreen(movieListUiState: MovieListUiState,
                            onMovieListItemClicked: (Movie) -> Unit,
                            modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        when(movieListUiState) {
            is MovieListUiState.Success -> {
                items(movieListUiState.movies) { movie ->
                    MovieGridItemCard(
                        movie = movie,
                        onMovieListItemClicked,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            is MovieListUiState.Loading -> {
                item {
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            is MovieListUiState.Error -> {
                item {
                    Text(
                        text = "Error: Something went wrong!",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieGridItemCard(movie: Movie,
                      onMovieGridItemClicked: (Movie) -> Unit,
                      modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = {
            onMovieGridItemClicked(movie)
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.padding(8.dp)
            ) {
                AsyncImage(
                    model = Constants.POSTER_IMAGE_BASE_URL + Constants.POSTER_IMAGE_WIDTH + movie.posterPath,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .width(92.dp)
                        .height(138.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}