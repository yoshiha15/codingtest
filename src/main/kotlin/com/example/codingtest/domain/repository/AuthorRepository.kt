package com.example.codingtest.domain.repository

import com.example.codingtest.domain.model.dto.AuthorDto

/**
 * AuthorRepository
 */
interface AuthorRepository {

    /**
     * 著者IDに紐つく著者を取得
     */
    fun getAuthor(authorId: Int): AuthorDto

    /**
     * 著者を登録
     * 著者IDはDBで自動発番
     */
    fun insertAuthor(name: String, birthday: String)

    /**
     * 著者を更新
     */
    fun updateAuthor(id: Int, name: String, birthday: String)
}