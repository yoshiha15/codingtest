package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.dto.AuthorBooksDto
import com.example.codingtest.domain.repository.AuthorBooksRepository
import org.springframework.stereotype.Service

/**
 * AuthorBooksServiceImpl
 */
@Service
class AuthorBooksServiceImpl(
    private val authorBooksRepository: AuthorBooksRepository
): AuthorBooksService {
    override fun getAuthorBooks(): List<AuthorBooksDto>{
        return authorBooksRepository.getAuthorBooks()
    }
}