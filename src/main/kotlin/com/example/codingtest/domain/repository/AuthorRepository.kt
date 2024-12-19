package com.example.codingtest.domain.repository

import org.jooq.Record

interface AuthorRepository {

    fun getAuthor(authorId: Int): Record

    fun insertAuthor(name: String, birthday: String)

    fun updateAuthor(id: Int, name: String, birthday: String)

}