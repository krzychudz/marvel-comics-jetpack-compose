package com.example.marvelcompose.models

data class ComicsModel (
    val title: String?,
    val description: String?,
    val thumbnail: ThumbnailModel,
    val id: Int
)