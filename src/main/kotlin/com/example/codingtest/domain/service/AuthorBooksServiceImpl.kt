package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.dto.AuthorBooksDto
import com.example.codingtest.domain.repository.AuthorBooksRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthorBooksServiceImpl(
    private val authorBooksRepository: AuthorBooksRepository
): AuthorBooksService {
    override fun getAuthorBooks(): List<AuthorBooksDto>{
        return authorBooksRepository.getAuthorBooks()
    }
}