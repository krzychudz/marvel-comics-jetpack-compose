package com.example.marvelcompose.screens.details

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.imageResource
import androidx.navigation.NavController
import com.example.marvelcompose.R
import com.example.marvelcompose.screens.movies.MovieScreenContent
import com.example.marvelcompose.screens.movies.components.TopBar

@Composable
fun DetailsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopBar(
                title = "Movies List",
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(asset = imageResource(id = R.drawable.arrow_back))
                    }
                })
        },
        bodyContent = {
            Text("Details Screen")
        }
    )
}