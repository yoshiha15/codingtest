package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.*
import com.example.codingtest.domain.repository.AuthorBooksRepository
import com.example.codingtest.domain.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookServiceImpl: BookService {

    @Autowired
    lateinit var authorBooksRepository: AuthorBooksRepository

    @Autowired
    lateinit var bookRepository: BookRepository

    @Transactional
    override fun insertBook(bookInsertRequest: BookInsertRequest) {

        // 書籍登録
        val insertedBookId: Int = bookRepository.insertBook(
            com.example.codingtest.domain.model.Book(
                bookId = 0, // 登録時に発番するため不要
                title = bookInsertRequest.title,
                price = bookInsertRequest.price,
                publish = bookInsertRequest.publish,
            ))

        for (authorId in bookInsertRequest.authorIds) {
            // 著者・書籍登録
            authorBooksRepository.insertAuthorBooks(
                authorId = authorId,
                bookId = insertedBookId
            )
        }
    }

    @Transactional
    override fun updateBook(bookUpdateRequest: BookUpdateRequest) {

        // 書籍情報更新　
        bookRepository.updateBook(
            com.example.codingtest.domain.model.Book(
                bookId = bookUpdateRequest.id,
                title = bookUpdateRequest.title,
                price = bookUpdateRequest.price,
                publish = bookUpdateRequest.publish,
        ))

        // 書籍IDに紐つく書籍・著者情報を削除後に再登録
        authorBooksRepository.deleteAuthorBooks(bookUpdateRequest.id)
        for (authorId in bookUpdateRequest.authorIds) {
            authorBooksRepository.insertAuthorBooks(authorId, bookUpdateRequest.id)
        }
    }
}