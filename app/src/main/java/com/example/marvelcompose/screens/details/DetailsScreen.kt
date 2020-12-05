package com.example.marvelcompose.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import com.example.marvelcompose.R
import com.example.marvelcompose.screens.movies.components.TopBar
import com.example.marvelcompose.screens.movies.view_models.MoviesScreenViewModel

@Composable
fun DetailsScreen(navController: NavController, comicsId: String?, viewModel: MoviesScreenViewModel) {
    Scaffold(
        topBar = {
            TopBar(
                title = viewModel.getComicsById(comicsId)?.title ?: "Comics Detail",
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(asset = imageResource(id = R.drawable.arrow_back))
                    }
                })
        },
        bodyContent = {
            DetailsScreenContent(comicsId, viewModel)
        }
    )
}

@Composable
fun DetailsScreenContent(comicsId: String?, viewModel: MoviesScreenViewModel) {
    val comicsData = viewModel.getComicsById(comicsId)

    Column(modifier = Modifier.fillMaxSize()) {
        Text(comicsData?.title ?: "Undefined")
        Text(comicsData?.description ?: "Undefined")
    }
}
