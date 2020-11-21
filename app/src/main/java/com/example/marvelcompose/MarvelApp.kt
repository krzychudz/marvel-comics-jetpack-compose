package com.example.marvelcompose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.marvelcompose.screens.details.DetailsScreen
import com.example.marvelcompose.screens.movies.MoviesScreen
import com.example.marvelcompose.screens.movies.movieScreenRouteName
import com.example.marvelcompose.ui.MarvelComposeTheme

@Composable
fun MarvelApp() {
    val navController = rememberNavController()

    MarvelComposeTheme {
        NavHost(navController = navController, startDestination = movieScreenRouteName) {
            composable(movieScreenRouteName) {
                MoviesScreen(navController)
            }
            composable("detailsScreen") {
                DetailsScreen(navController)
            }
        }
    }
}