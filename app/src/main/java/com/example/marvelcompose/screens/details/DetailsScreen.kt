package com.example.marvelcompose.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import com.example.marvelcompose.R
import com.example.marvelcompose.models.ComicsModel
import com.example.marvelcompose.screens.movies.components.NetworkImage
import com.example.marvelcompose.screens.movies.components.TopBar
import com.example.marvelcompose.screens.movies.view_models.MoviesScreenViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    comicsId: String?,
    viewModel: MoviesScreenViewModel
) {
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
    val networkUrl = "${comicsData?.thumbnail?.path}.${comicsData?.thumbnail?.extension}".replace(
        "http",
        "https"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        NetworkImage(networkUrl, modifier = Modifier.fillMaxWidth().height(300.dp).padding(8.dp))
        Spacer(Modifier.width(10.dp))
        DetailsSection(comicsData)
    }
}

@Composable
fun DetailsSection(comicsData: ComicsModel?) {
    Column(Modifier.padding(16.dp)) {
        Text(
            comicsData?.title ?: "Undefined",
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.width(16.dp))
        Text(
            comicsData?.description ?: "Undefined",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
