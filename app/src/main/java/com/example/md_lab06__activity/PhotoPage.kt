package com.example.md_lab06__activity

data class PhotoPage(
    val page: Int,
    val pages: Int,
    val perPage: Int,
    val total: Int,
    val photo: List<Photo>
)
