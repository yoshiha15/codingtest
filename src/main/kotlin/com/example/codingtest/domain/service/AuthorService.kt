package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.response.GetAuthorResponse
import com.example.codingtest.domain.model.request.InsertAuthorRequest
import com.example.codingtest.domain.model.request.UpdateAuthorRequest

interface AuthorService {
    fun getAuthor(authorId: Int): GetAuthorResponse

    fun insertAuthor(insertAuthorRequest: InsertAuthorRequest)

    fun updateAuthor(updateAuthorRequest: UpdateAuthorRequest)

}