package com.example.themoviedbv24.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.themoviedbv24.viewmodel.SelectedMovieReviewsUiState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalUriHandler
import com.example.themoviedbv24.model.MovieReviews
import com.example.themoviedbv24.model.MovieVideo
import com.example.themoviedbv24.utils.Constants
import com.example.themoviedbv24.viewmodel.SelectedMovieVideosUiState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun MovieExtraScreen(
    selectedMovieReviewsUiState: SelectedMovieReviewsUiState,
    selectedMovieVideosUiState: SelectedMovieVideosUiState,
    modifier: Modifier = Modifier){
    Column(
        modifier = modifier.fillMaxHeight()
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            modifier = Modifier.weight(1f)
        ) {
            when(selectedMovieReviewsUiState){
                is SelectedMovieReviewsUiState.Success -> {
                    items(selectedMovieReviewsUiState.movieReviewsList) { movieReview ->
                        MovieReviewItemCard(movieReview, modifier)
                    }
                }
                is SelectedMovieReviewsUiState.Loading -> {
                    item{
                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                is SelectedMovieReviewsUiState.Error -> {
                    item{
                        Text(
                            text = "Error...",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            modifier = Modifier.weight(1f)
        ) {
            when(selectedMovieVideosUiState){
                is SelectedMovieVideosUiState.Success -> {
                    items(selectedMovieVideosUiState.movieVideosList) { movieVideo ->
                        MovieVideoItemCard(movieVideo, modifier)
                    }
                }
                is SelectedMovieVideosUiState.Loading -> {
                    item{
                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                is SelectedMovieVideosUiState.Error -> {
                    item{
                        Text(
                            text = "Error...",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieReviewItemCard(
    movieReviews: MovieReviews,
    modifier: Modifier = Modifier){
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = modifier
                .width(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Badge{ Text(formatDate(movieReviews.updated_at)) }
            Text(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false),
                text = movieReviews.content,
                style = MaterialTheme.typography.bodySmall,
                //overflow = TextOverflow.Ellipsis
            )
            Button(
                onClick = { uriHandler.openUri(movieReviews.url) },
                modifier = modifier.fillMaxWidth()) {
                Text(text = "link review")
            }
        }
    }
}

@Composable
fun MovieVideoItemCard(
    movieVideo: MovieVideo,
    modifier: Modifier = Modifier){
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = modifier
                .width(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false),
                text = movieVideo.name,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val instant = Instant.from(inputFormatter.parse(dateString))
    val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return outputFormatter.format(instant.atZone(ZoneId.systemDefault()))
}