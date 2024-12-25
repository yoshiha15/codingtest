package com.example.codingtest.domain.repository

import com.example.codingtest.domain.model.dto.AuthorBooksDto

/**
 * AuthorBooksRepository
 */
interface AuthorBooksRepository {

    /**
     * 著者・書籍の情報を一覧で取得
     */
    fun getAuthorBooks(): List<AuthorBooksDto>

    /**
     * 著者IDに紐つく書籍IDのリストを取得
     */
    fun getBookIds(authorId: Int): List<Int>

    /**
     * 著者IDと書籍IDの関連を登録
     */
    fun insertAuthorBooks(authorId: Int, bookId: Int)

    /**
     * 著者IDと書籍IDの関連を削除
     */
    fun deleteAuthorBooks(bookId: Int)
}
