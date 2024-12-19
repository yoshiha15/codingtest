package com.example.codingtest.domain.model

data class AuthorGetResponse(
    val authorId: Int,
    val name: String,
    val birthday: String,
    val books: List<Book>
)
