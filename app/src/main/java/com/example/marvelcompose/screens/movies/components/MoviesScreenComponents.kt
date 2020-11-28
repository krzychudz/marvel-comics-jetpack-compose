package com.example.marvelcompose.screens.movies.components

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.imageLoader
import coil.request.ImageRequest
import com.example.marvelcompose.R
import com.example.marvelcompose.models.ComicsModel
import com.example.marvelcompose.screens.movies.view_models.MoviesScreenViewModel

@Composable
fun TopBar(title: String, navigationIcon: @Composable (() -> Unit)? = null) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
        },
        backgroundColor = colors.primary,
        navigationIcon = navigationIcon

    )
}

@Composable
fun CenteredProgressBar() {
    Box(
        Modifier.fillMaxSize(),
        alignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ComicsResultSection(
    data: List<ComicsModel>,
    moviesScreenViewModel: MoviesScreenViewModel,
    navCallback: (movieId: String?) -> Unit
) {
    Surface(Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)) {
        LazyColumnForIndexed(data) { index, item ->
            ComicsElement(comicsModel = item, navCallback)

            if (index == data.size - 1 && moviesScreenViewModel.canFetchData()) {
                moviesScreenViewModel.fetchMovies()
                Box(alignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun NoResultInfo() {
    Box(
        Modifier.fillMaxSize(),
        alignment = Alignment.Center
    ) {
        Text("No comics found")
    }
}


@Composable
fun ComicsElement(comicsModel: ComicsModel, navCallback: (movieId: String?) -> Unit) {

    Card(
        modifier = Modifier.clickable(onClick = { navCallback.invoke(comicsModel.id.toString()) })
            .fillMaxWidth().padding(bottom = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            NetworkImage(
                url = "${comicsModel.thumbnail.path}.${comicsModel.thumbnail.extension}".replace(
                    "http",
                    "https"
                ), Modifier.fillMaxHeight().width(100.dp).clipToBounds()
            )
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = comicsModel.title ?: "No title found",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = comicsModel.description ?: "No description found",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun NetworkImage(url: String, modifier: Modifier) {
    var drawable by remember { mutableStateOf<Drawable?>(null) }

    val request = ImageRequest.Builder(ContextAmbient.current)
        .data(url)
        .target {
            drawable = it
        }
        .build()

    ContextAmbient.current.imageLoader.enqueue(request)

    if (drawable == null) {
        Text("Waiting")
    } else {
        val bitmapDrawable = drawable as BitmapDrawable
        Image(bitmapDrawable.bitmap.asImageAsset(), modifier = modifier)
    }
}

