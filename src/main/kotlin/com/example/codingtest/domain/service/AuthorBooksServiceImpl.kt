package com.example.codingtest.domain.service

import com.example.codingtest.domain.repository.AuthorBooksRepository
import org.jooq.Record
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthorBooksServiceImpl: AuthorBooksService {

    @Autowired
    lateinit var authorBooksRepository: AuthorBooksRepository

    override fun getAuthorBooks(): List<Record>{
        return authorBooksRepository.getAuthorBooks()
    }
}