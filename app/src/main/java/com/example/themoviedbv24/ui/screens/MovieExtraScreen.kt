package com.example.themoviedbv24.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.themoviedbv24.model.MovieReviews
import com.example.themoviedbv24.model.MovieVideo
import com.example.themoviedbv24.viewmodel.SelectedMovieVideosUiState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
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
            when (selectedMovieReviewsUiState) {
                is SelectedMovieReviewsUiState.Success -> {
                    items(selectedMovieReviewsUiState.movieReviewsList) { movieReview ->
                        MovieReviewItemCard(movieReview, modifier)
                    }
                }

                is SelectedMovieReviewsUiState.Loading -> {
                    item {
                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                is SelectedMovieReviewsUiState.Error -> {
                    item {
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
                .width(200.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Badge{ Text(formatDate(movieReviews.updated_at)) }
            Text(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false),
                text = movieReviews.content,
                style = MaterialTheme.typography.bodySmall
            )
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
            ExoPlayerView()
        }
    }
}


@Composable
fun ExoPlayerView() {
    // Get the current context
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = ExoPlayer.Builder(context).build()

    val EXAMPLE_VIDEO_URI = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    // Create a MediaSource
    val mediaSource = remember(EXAMPLE_VIDEO_URI) {
        MediaItem.fromUri(EXAMPLE_VIDEO_URI)
    }
    // Set MediaSource to ExoPlayer
    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }
    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
    // Use AndroidView to embed an Android View (PlayerView) into Compose
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Set your desired height
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val instant = Instant.from(inputFormatter.parse(dateString))
    val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return outputFormatter.format(instant.atZone(ZoneId.systemDefault()))
}