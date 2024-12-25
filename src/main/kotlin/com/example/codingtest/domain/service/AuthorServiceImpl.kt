package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.response.GetAuthorResponse
import com.example.codingtest.domain.model.request.InsertAuthorRequest
import com.example.codingtest.domain.model.request.UpdateAuthorRequest
import com.example.codingtest.domain.repository.AuthorBooksRepository
import com.example.codingtest.domain.repository.AuthorRepository
import com.example.codingtest.domain.repository.BookRepository
import com.example.codingtest.domain.model.dto.AuthorDto
import com.example.codingtest.domain.model.dto.BookDto
import org.springframework.stereotype.Service

/**
 * AuthorServiceImpl
 */
@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository,
    private val authorBooksRepository: AuthorBooksRepository,
    private val bookRepository: BookRepository,
): AuthorService {

    override fun getAuthor(authorId: Int): GetAuthorResponse {

        // 著者情報取得
        val author: AuthorDto = authorRepository.getAuthor(authorId)

        // 著者に紐つく書籍ID取得
        val bookIds: List<Int> = authorBooksRepository.getBookIds(authorId)

        // 書籍情報を取得
        val books: List<BookDto> = bookRepository.getBooks(bookIds)

        return GetAuthorResponse(
            authorId = author.authorId,
            name = author.name,
            birthday = author.birthday,
            books = books
        )
    }

    override fun insertAuthor(insertAuthorRequest: InsertAuthorRequest) {
        authorRepository.insertAuthor(insertAuthorRequest.name, insertAuthorRequest.birthday)
    }

    override fun updateAuthor(updateAuthorRequest: UpdateAuthorRequest) {
        authorRepository.updateAuthor(updateAuthorRequest.authorId, updateAuthorRequest.name, updateAuthorRequest.birthday)
    }

}
