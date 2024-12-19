package com.example.codingtest.domain.service


import com.example.codingtest.domain.model.BookInsertRequest
import com.example.codingtest.domain.model.BookUpdateRequest

interface BookService {
    fun insertBook(bookInsertRequest: BookInsertRequest)
    fun updateBook(bookUpdateRequest: BookUpdateRequest)
}