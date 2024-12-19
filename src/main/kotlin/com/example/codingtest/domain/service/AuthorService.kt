package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.AuthorGetResponse
import com.example.codingtest.domain.model.AuthorInsertRequest
import com.example.codingtest.domain.model.AuthorUpdateRequest

interface AuthorService {
    fun getAuthor(authorId: Int): AuthorGetResponse
    fun insertAuthor(authorInsertRequest: AuthorInsertRequest)
    fun updateAuthor(authorUpdateRequest: AuthorUpdateRequest)

}