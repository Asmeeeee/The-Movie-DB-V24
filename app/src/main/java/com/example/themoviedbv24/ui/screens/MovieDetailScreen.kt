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
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.themoviedbv24.R
import com.example.themoviedbv24.database.MoviesRepository
import com.example.themoviedbv24.model.Movie
import com.example.themoviedbv24.model.MovieDetail
import com.example.themoviedbv24.utils.Constants
import com.example.themoviedbv24.utils.Constants.IMDB_BASE_URL
import com.example.themoviedbv24.viewmodel.MovieDBViewModel
import com.example.themoviedbv24.viewmodel.SelectedMovieUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieDBViewModel: MovieDBViewModel,
    selectedMovieUiState: SelectedMovieUiState,
    modifier: Modifier = Modifier
) {
    val selectedMovieUiState = movieDBViewModel.selectedMovieUiState
    when (selectedMovieUiState) {
        is SelectedMovieUiState.Success -> {
            val uriHandler = LocalUriHandler.current
            Column(Modifier.width(IntrinsicSize.Max)) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp)) {
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
                        Badge{ Text(text = genre.name,) }
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
                Button(
                    onClick = { uriHandler.openUri(IMDB_BASE_URL+selectedMovieUiState.movieDetail.imdbId) },
                    modifier = modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.open_imdb))
                }
                Spacer(modifier = Modifier.size(8.dp))
                if(selectedMovieUiState.movieDetail.homepage.isNotEmpty()){
                    Button(
                        onClick = { uriHandler.openUri(selectedMovieUiState.movieDetail.homepage) },
                        modifier = modifier.fillMaxWidth()) {
                        Text(text = stringResource(id = R.string.open_homepage))
                    }
                }
                Row {
                    Text(text = "Favorite", style = MaterialTheme.typography.bodyLarge)
                    Switch(checked = selectedMovieUiState.is_Favorite, onCheckedChange = {
                        if(it){
                            movieDBViewModel.saveMovie(selectedMovieUiState.movieDetail)
                        }else{
                            movieDBViewModel.deleteMovie(selectedMovieUiState.movieDetail)
                        }
                    })
                }
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