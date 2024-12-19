package com.example.codingtest.domain.repository

import org.jooq.Record

interface AuthorBooksRepository {
    fun getAuthorBooks(): List<Record>

    fun getBookIds(authorId: Int): List<Record>

    fun insertAuthorBooks(authorId: Int, bookId: Int)

    fun deleteAuthorBooks(bookId: Int)
}