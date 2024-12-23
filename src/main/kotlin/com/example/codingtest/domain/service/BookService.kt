package com.example.codingtest.domain.service


import com.example.codingtest.domain.model.request.InsertBookRequest
import com.example.codingtest.domain.model.request.UpdateBookRequest

/**
 * BookService
 */
interface BookService {
    /**
     * 書籍と著者・書籍の関連を登録
     */
    fun insertBook(insertBookRequest: InsertBookRequest)

    /**
     * 書籍と著者・書籍の関連を更新
     */
    fun updateBook(bookUpdateRequest: UpdateBookRequest)
}