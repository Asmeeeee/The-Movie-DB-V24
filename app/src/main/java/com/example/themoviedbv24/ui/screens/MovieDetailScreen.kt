package com.example.themoviedbv24.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.themoviedbv24.database.MoviesRepository
import com.example.themoviedbv24.model.Movie
import com.example.themoviedbv24.utils.Constants
import com.example.themoviedbv24.utils.Constants.IMDB_BASE_URL
import com.example.themoviedbv24.viewmodel.SelectedMovieUiState


@Composable
fun MovieDetailScreen(
    selectedMovieUiState: SelectedMovieUiState,
    modifier: Modifier = Modifier
) {
    when (selectedMovieUiState) {
        is SelectedMovieUiState.Success -> {
            val uriHandler = LocalUriHandler.current
            Column(Modifier.width(IntrinsicSize.Max)) {
                Box(Modifier.fillMaxWidth().padding(0.dp)) {
                    AsyncImage(
                        model = Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + selectedMovieUiState.movieDetail.backdropPath,
                        contentDescription = selectedMovieUiState.movieDetail.title,
                        modifier = modifier,
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = selectedMovieUiState.movieDetail.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Row{
                    selectedMovieUiState.movieDetail.genres.forEach { genre ->
                        Text(
                            text = genre.name,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = selectedMovieUiState.movieDetail.releaseDate,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = selectedMovieUiState.movieDetail.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.size(8.dp))
                ClickableText(
                    text = buildAnnotatedString {
                        append(selectedMovieUiState.movieDetail.homepage)
                        addStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline
                            ),
                            start = 0,
                            end = length
                        )
                    },
                    onClick = { selectedMovieUiState.movieDetail.homepage?.let { it1 ->
                        uriHandler.openUri(
                            it1
                        )
                    } }
                )
                Spacer(modifier = Modifier.size(8.dp))
                ClickableText(
                    text = buildAnnotatedString {
                        append(IMDB_BASE_URL+selectedMovieUiState.movieDetail.imdbId)
                        addStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline
                            ),
                            start = 0,
                            end = length
                        )
                    },
                    onClick = { uriHandler.openUri(IMDB_BASE_URL+selectedMovieUiState.movieDetail.imdbId) }
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
        is SelectedMovieUiState.Loading -> {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
        is SelectedMovieUiState.Error -> {
            Text(
                text = "Error...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}