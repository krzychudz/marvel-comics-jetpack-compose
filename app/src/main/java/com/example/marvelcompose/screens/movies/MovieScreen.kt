package com.example.marvelcompose.screens.movies

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.ui.tooling.preview.Preview
import com.example.marvelcompose.models.ComicsModel
import com.example.marvelcompose.screens.movies.components.*
import com.example.marvelcompose.screens.movies.view_models.MoviesScreenViewModel

const val movieScreenRouteName = "movieScreen"

@Composable
fun MoviesScreen(navController: NavController) {
    Scaffold(
        topBar = { TopBar("Movies List") },
        bodyContent = {
            MovieScreenContent(navCallback = {
                navController.navigate("detailsScreen")
            })
        }
    )
}

@Composable
fun MovieScreenContent(navCallback: () -> Unit, moviesScreenViewModel: MoviesScreenViewModel = viewModel()) {
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
fun ComicsContainer(moviesScreenViewModel: MoviesScreenViewModel, navCallback: () -> Unit) {
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
