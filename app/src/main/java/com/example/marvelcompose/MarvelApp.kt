package com.example.marvelcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marvelcompose.screens.details.DetailsScreen
import com.example.marvelcompose.screens.movies.MoviesScreen
import com.example.marvelcompose.screens.movies.movieScreenRouteName
import com.example.marvelcompose.screens.movies.view_models.MoviesScreenViewModel
import com.example.marvelcompose.ui.MarvelComposeTheme

@Composable
fun MarvelApp() {
    val navController = rememberNavController()
    val mainViewModel: MoviesScreenViewModel = viewModel()

    MarvelComposeTheme {
        NavHost(navController = navController, startDestination = movieScreenRouteName) {
            composable(movieScreenRouteName) {
                MoviesScreen(navController, mainViewModel)
            }
            composable("detailsScreen/{comicsId}") { backStackEntry ->
                val comicsId = backStackEntry.arguments?.getString("comicsId")
                DetailsScreen(navController, comicsId, mainViewModel)
            }
        }
    }
}