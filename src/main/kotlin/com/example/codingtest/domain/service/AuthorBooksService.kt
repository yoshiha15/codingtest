package com.example.codingtest.domain.service

import org.jooq.Record

interface AuthorBooksService {

    fun getAuthorBooks(): List<Record>;
}