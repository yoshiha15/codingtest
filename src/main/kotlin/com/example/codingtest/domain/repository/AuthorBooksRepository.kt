package com.example.codingtest.domain.repository

import com.example.codingtest.domain.model.dto.AuthorBooksDto

interface AuthorBooksRepository {
    fun getAuthorBooks(): List<AuthorBooksDto>

    fun getBookIds(authorId: Int): List<Int>

    fun insertAuthorBooks(authorId: Int, bookId: Int)

    fun deleteAuthorBooks(bookId: Int)
}