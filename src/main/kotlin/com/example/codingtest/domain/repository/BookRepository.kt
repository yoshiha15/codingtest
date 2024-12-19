package com.example.codingtest.domain.repository

import com.example.codingtest.domain.model.Book
import org.jooq.Record

interface BookRepository {
    fun getBooks(bookIds: List<Int>): List<Record>

    fun insertBook(book: Book): Int

    fun updateBook(book: Book)
}