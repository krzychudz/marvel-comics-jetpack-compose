package com.example.marvelcompose.screens.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.marvelcompose.models.ComicsModel
import com.example.marvelcompose.screens.movies.components.CenteredProgressBar
import com.example.marvelcompose.screens.movies.components.ComicsResultSection
import com.example.marvelcompose.screens.movies.components.NoResultInfo
import com.example.marvelcompose.screens.movies.components.TopBar
import com.example.marvelcompose.screens.movies.view_models.MoviesScreenViewModel

const val movieScreenRouteName = "movieScreen"

@Composable
fun MoviesScreen(navController: NavController, viewModel: MoviesScreenViewModel) {
    Scaffold(
        topBar = { TopBar("Movies List") },
        bodyContent = {
            MovieScreenContent(navCallback = { movieId ->
                navController.navigate("detailsScreen/$movieId")
            }, viewModel)
        }
    )
}

@Composable
fun MovieScreenContent(navCallback: (movieId: String?) -> Unit, moviesScreenViewModel: MoviesScreenViewModel) {
    val comicsSearchData: String? by moviesScreenViewModel.comicsSearchData.observeAsState(null)

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = comicsSearchData ?: "",
            onValueChange = { newText -> moviesScreenViewModel.updateComicsSearchData(newText) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search for comics") })
        ComicsContainer(moviesScreenViewModel, navCallback)
    }
}

@Composable
fun ComicsContainer(moviesScreenViewModel: MoviesScreenViewModel, navCallback: (movieId: String?) -> Unit) {
    val comicsData: List<ComicsModel>? by moviesScreenViewModel.comicsData.observeAsState(null)

    when {
        comicsData?.isEmpty() == true -> NoResultInfo()
        comicsData == null -> CenteredProgressBar()
        else -> {
           comicsData?.let { data ->
               ComicsResultSection(data, moviesScreenViewModel, navCallback)
           }
        }
    }
}
