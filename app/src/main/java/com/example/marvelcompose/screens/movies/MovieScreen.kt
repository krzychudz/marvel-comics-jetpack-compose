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
import androidx.ui.tooling.preview.Preview
import com.example.marvelcompose.models.ComicsModel
import com.example.marvelcompose.screens.movies.components.CenteredProgressBar
import com.example.marvelcompose.screens.movies.components.ComicsElement
import com.example.marvelcompose.screens.movies.components.TopBar
import com.example.marvelcompose.screens.movies.view_models.MoviesScreenViewModel

const val movieScreenRouteName = "movieScreen"

@Composable
@Preview
fun MoviesScreen() {
    Scaffold(
        topBar = { TopBar("Movies List") },
        bodyContent = {
            MovieScreenContent()
        }
    )
}

@Composable
fun MovieScreenContent(moviesScreenViewModel: MoviesScreenViewModel = viewModel()) {
    val comicsSearchData: String by moviesScreenViewModel.comicsSearchData.observeAsState("")

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = comicsSearchData,
            onValueChange = { newText -> moviesScreenViewModel.updateComicsSearchData(newText) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search for comics") })
        ComicsContainer(moviesScreenViewModel)
    }
}

@Composable
fun ComicsContainer(moviesScreenViewModel: MoviesScreenViewModel) {
    val comicsData: List<ComicsModel> by moviesScreenViewModel.comicsData.observeAsState(listOf())

    if (comicsData.isEmpty()) {
        CenteredProgressBar()
    } else {
        Surface(Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)) {
            LazyColumnForIndexed(comicsData) { index, item ->
                ComicsElement(comicsModel = item)
                if (index == comicsData.size - 1 && moviesScreenViewModel.canFetchData()) {
                    moviesScreenViewModel.fetchMovies()
                    Box(alignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
