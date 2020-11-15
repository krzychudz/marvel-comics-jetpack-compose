package com.example.marvelcompose.screens.movies

import android.view.Gravity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.example.marvelcompose.models.ComicsModel
import com.example.marvelcompose.screens.movies.components.TopBar
import com.example.marvelcompose.screens.movies.view_models.MoviesScreenViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.marvelcompose.screens.movies.components.CenteredProgressBar
import com.example.marvelcompose.screens.movies.components.ComicsElement

const val movieScreenRouteName = "movieScreen"

@Composable
@Preview
fun MoviesScreen(moviesScreenViewModel: MoviesScreenViewModel = viewModel()) {
    Scaffold(
        topBar = { TopBar("Movies List") },
        bodyContent = {
            MovieScreenContent(moviesScreenViewModel)
        }
    )
}

@Composable
fun MovieScreenContent(moviesScreenViewModel: MoviesScreenViewModel) {
    val comicsData: List<ComicsModel> by moviesScreenViewModel.comicsData.observeAsState(listOf())

    if (comicsData.isEmpty()) {
        CenteredProgressBar()
    } else {
        ScrollableColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
            comicsData.map {
                ComicsElement(comicsModel = it)
            }
        }
    }
}
