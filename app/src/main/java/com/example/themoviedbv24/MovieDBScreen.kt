package com.example.themoviedbv24

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.themoviedbv24.ui.screens.MovieDetailScreen
import com.example.themoviedbv24.ui.screens.MovieExtraScreen
import com.example.themoviedbv24.ui.screens.MovieGridLayoutScreen
import com.example.themoviedbv24.ui.screens.MovieListScreen
import com.example.themoviedbv24.viewmodel.MovieDBViewModel

enum class MovieDBScreen(@StringRes val title: Int) {
    List(title = R.string.popular_movies),
    Grid(title = R.string.popular_movies),
    Detail(title = R.string.movie_detail),
    Extra(title = R.string.movie_extra_data),
}


/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDBAppBar(
    currentScreen: MovieDBScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navigateToListScreen: () -> Unit,
    navigateToExtraScreen: () -> Unit,
    navigateToGridScreen: () -> Unit,
    movieDBViewModel: MovieDBViewModel,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (currentScreen.name == MovieDBScreen.List.name || currentScreen.name == MovieDBScreen.Grid.name) {
                IconButton(onClick = {
                    if(currentScreen.name == MovieDBScreen.List.name) navigateToGridScreen() else navigateToListScreen()
                }) {
                    Icon(
                        imageVector = Icons.Filled.List ,
                        contentDescription = stringResource(id = R.string.more_vert)
                    )
                }
                IconButton(onClick = {
                    menuExpanded = !menuExpanded
                }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Open Menu to select different movie lists"
                    )
                }
                DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                    DropdownMenuItem(
                        onClick = {
                            movieDBViewModel.getPopularMovies()
                            menuExpanded = false

                        },
                        text = {
                            Text(stringResource(R.string.popular_movies))
                        }
                    )
                    DropdownMenuItem(
                        onClick = {
                            movieDBViewModel.getTopRatedMovies()
                            menuExpanded = false

                        },
                        text = {
                            Text(stringResource(R.string.top_rated_movies))
                        }
                    )
                    DropdownMenuItem(
                        onClick = {
                            movieDBViewModel.getSavedMovies()
                            menuExpanded = false

                        },
                        text = {
                            Text(stringResource(R.string.saved_movies))
                        }
                    )
                }
            }
            if(currentScreen.name == MovieDBScreen.Detail.name){
                IconButton(onClick = {
                    navigateToExtraScreen()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = stringResource(id = R.string.info)
                    )
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDBApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MovieDBScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieDBScreen.List.name
    )

    val movieDBViewModel: MovieDBViewModel = viewModel(factory = MovieDBViewModel.Factory)

    Scaffold(
        topBar = {
            MovieDBAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null && currentScreen.name != MovieDBScreen.List.name && currentScreen.name != MovieDBScreen.Grid.name,
                navigateUp = { navController.navigateUp() },
                navigateToListScreen = { navController.navigate(MovieDBScreen.List.name) },
                navigateToExtraScreen = { navController.navigate(MovieDBScreen.Extra.name) },
                navigateToGridScreen = { navController.navigate(MovieDBScreen.Grid.name) },
                movieDBViewModel = movieDBViewModel
            )
        }
    ) { innerPadding ->
        val movieDBViewModel: MovieDBViewModel = viewModel(factory = MovieDBViewModel.Factory)

        NavHost(
            navController = navController,
            startDestination = MovieDBScreen.Grid.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = MovieDBScreen.List.name) {
                MovieListScreen(
                    movieListUiState = movieDBViewModel.movieListUiState,
                    onMovieListItemClicked = {
                        movieDBViewModel.setSelectedMovieDetail(it.id)
                        movieDBViewModel.setSelectedMovieExtraUiState(it.id)
                        movieDBViewModel.setSelectedMovieVideosUiState(it.id)
                        navController.navigate(MovieDBScreen.Detail.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = MovieDBScreen.Grid.name){
                MovieGridLayoutScreen(
                    movieListUiState = movieDBViewModel.movieListUiState,
                    onMovieListItemClicked = {
                        movieDBViewModel.setSelectedMovieDetail(it.id)
                        movieDBViewModel.setSelectedMovieExtraUiState(it.id)
                        movieDBViewModel.setSelectedMovieVideosUiState(it.id)
                        navController.navigate(MovieDBScreen.Detail.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
            composable(route = MovieDBScreen.Detail.name) {
                MovieDetailScreen(
                    movieDBViewModel = movieDBViewModel,
                    selectedMovieUiState = movieDBViewModel.selectedMovieUiState,
                    modifier = Modifier
                )
            }
            composable(route = MovieDBScreen.Extra.name){
                MovieExtraScreen(
                    selectedMovieReviewsUiState = movieDBViewModel.selectedMovieReviewsUiState,
                    selectedMovieVideosUiState = movieDBViewModel.selectedMovieVideosUiState,
                    modifier = Modifier
                        .padding(6.dp))
            }
        }
    }
}