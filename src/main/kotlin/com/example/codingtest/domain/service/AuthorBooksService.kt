package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.dto.AuthorBooksDto

/**
 * AuthorBooksService
 */
interface AuthorBooksService {

    /**
     * 著者・書籍の情報を一覧で取得する
     */
    fun getAuthorBooks(): List<AuthorBooksDto>;
}
