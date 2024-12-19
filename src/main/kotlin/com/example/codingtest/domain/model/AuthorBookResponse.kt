package com.example.codingtest.domain.model

data class AuthorBookResponse(
    val authorId: Int,
    val name: String,
    val birthday: String,
    val bookId: Int,
    val title: String,
    val price: Int,
    val publish: String,
)
