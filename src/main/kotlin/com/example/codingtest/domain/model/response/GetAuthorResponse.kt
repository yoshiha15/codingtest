package com.example.codingtest.domain.model.response

import com.example.codingtest.domain.model.dto.BookDto

/**
 * GetAuthorResponse
 */
data class GetAuthorResponse(
    val authorId: Int,
    val name: String,
    val birthday: String,
    val books: List<BookDto>
)
