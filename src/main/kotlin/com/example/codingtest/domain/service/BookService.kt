package com.example.codingtest.domain.service


import com.example.codingtest.domain.model.request.InsertBookRequest
import com.example.codingtest.domain.model.request.UpdateBookRequest

interface BookService {
    fun insertBook(insertBookRequest: InsertBookRequest)
    fun updateBook(bookUpdateRequest: UpdateBookRequest)
}