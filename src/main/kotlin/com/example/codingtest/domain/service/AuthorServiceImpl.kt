package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.AuthorGetResponse
import com.example.codingtest.domain.model.AuthorInsertRequest
import com.example.codingtest.domain.model.AuthorUpdateRequest
import com.example.codingtest.domain.repository.AuthorBooksRepository
import com.example.codingtest.domain.repository.AuthorRepository
import com.example.codingtest.domain.repository.BookRepository
import com.example.codingtest.domain.tables.Author
import com.example.codingtest.domain.tables.AuthorBooks
import com.example.codingtest.domain.tables.Book
import org.jooq.Record
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl: AuthorService {

    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    lateinit var authorBooksRepository: AuthorBooksRepository

    @Autowired
    lateinit var bookRepository: BookRepository


    override fun getAuthor(authorId: Int): AuthorGetResponse {

        // 著者情報取得
        val author: Record = authorRepository.getAuthor(authorId)

        // 著者に紐つく書籍ID取得
        val bookIds: List<Int> = authorBooksRepository.getBookIds(authorId).map { it.getValue(AuthorBooks.AUTHOR_BOOKS.BOOK_ID) }

        // 書籍情報を取得
        val books = bookRepository.getBooks(bookIds).map { com.example.codingtest.domain.model.Book(
            bookId = it.getValue(Book.BOOK.ID),
            title = it.getValue(Book.BOOK.TITLE),
            price = it.getValue(Book.BOOK.PRICE),
            publish = it.getValue(Book.BOOK.PUBLISH),
        ) }

        return AuthorGetResponse(
            authorId = author.getValue(Author.AUTHOR.ID),
            name = author.getValue(Author.AUTHOR.NAME),
            birthday = author.getValue(Author.AUTHOR.BIRTHDAY),
            books = books
        )
    }

    override fun insertAuthor(authorInsertRequest: AuthorInsertRequest) {
        authorRepository.insertAuthor(authorInsertRequest.name, authorInsertRequest.birthday)
    }

    override fun updateAuthor(authorUpdateRequest: AuthorUpdateRequest) {
        authorRepository.updateAuthor(authorUpdateRequest.id, authorUpdateRequest.name, authorUpdateRequest.birthday)
    }

}