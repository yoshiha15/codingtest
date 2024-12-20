package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.dto.AuthorBooksDto

interface AuthorBooksService {

    fun getAuthorBooks(): List<AuthorBooksDto>;
}