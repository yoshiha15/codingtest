package com.example.codingtest.domain.repository

import com.example.codingtest.domain.model.dto.BookDto

/**
 * BookRepository
 */
interface BookRepository {

    /**
     * 書籍IDに紐づく書籍情報を取得
     */
    fun getBooks(bookIds: List<Int>): List<BookDto>

    /**
     * 書籍を登録
     * 書籍IDはDBで自動発番
     */
    fun insertBook(bookDto: BookDto): Int

    /**
     * 書籍を更新
     */
    fun updateBook(bookDto: BookDto)
}