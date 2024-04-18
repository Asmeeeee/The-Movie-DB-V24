package com.example.themoviedbv24.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.themoviedbv24.viewmodel.SelectedMovieExtraUiState

@Composable
fun MovieExtraScreen(
    selectedMovieExtraUiState: SelectedMovieExtraUiState,
    modifier: Modifier = Modifier){
    when(selectedMovieExtraUiState){
        is SelectedMovieExtraUiState.Success -> {
            Text(text = "OUI")
        }
        is SelectedMovieExtraUiState.Loading -> {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
        is SelectedMovieExtraUiState.Error -> {
            Text(
                text = "Error...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}