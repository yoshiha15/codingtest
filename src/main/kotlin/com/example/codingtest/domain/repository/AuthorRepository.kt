package com.example.codingtest.domain.repository

import com.example.codingtest.domain.model.dto.AuthorDto
import org.jooq.Record

interface AuthorRepository {

    fun getAuthor(authorId: Int): AuthorDto

    fun insertAuthor(name: String, birthday: String)

    fun updateAuthor(id: Int, name: String, birthday: String)

}