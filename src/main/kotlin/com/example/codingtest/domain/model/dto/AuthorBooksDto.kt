package com.example.codingtest.domain.model.dto

data class AuthorBooksDto(
    val authorId: Int,
    val name: String,
    val birthday: String,
    val bookId: Int,
    val title: String,
    val price: Int,
    val publish: String
)