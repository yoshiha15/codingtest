package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.response.GetAuthorResponse
import com.example.codingtest.domain.model.request.InsertAuthorRequest
import com.example.codingtest.domain.model.request.UpdateAuthorRequest

/**
 * AuthorService
 */
interface AuthorService {
    /**
     * 著者と著者に紐づく書籍のリストを取得
     */
    fun getAuthor(authorId: Int): GetAuthorResponse

    /**
     * 著者を登録
     */
    fun insertAuthor(insertAuthorRequest: InsertAuthorRequest)

    /**
     * 著者を更新
     */
    fun updateAuthor(updateAuthorRequest: UpdateAuthorRequest)
}
