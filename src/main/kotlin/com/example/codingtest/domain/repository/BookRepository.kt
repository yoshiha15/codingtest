package com.example.codingtest.domain.repository

import com.example.codingtest.domain.model.dto.BookDto
import org.jooq.Record

interface BookRepository {
    fun getBooks(bookIds: List<Int>): List<BookDto>

    fun insertBook(bookDto: BookDto): Int

    fun updateBook(bookDto: BookDto)
}