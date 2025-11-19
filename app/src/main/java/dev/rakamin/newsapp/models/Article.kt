package dev.rakamin.newsapp.models

data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlTomage: String?,
    val publisheAt: String?,
    val Content: String
)
