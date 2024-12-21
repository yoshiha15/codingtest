package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.dto.BookDto
import com.example.codingtest.domain.model.request.InsertBookRequest
import com.example.codingtest.domain.model.request.UpdateBookRequest
import com.example.codingtest.domain.repository.AuthorBooksRepository
import com.example.codingtest.domain.repository.AuthorRepository
import com.example.codingtest.domain.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookServiceImpl(
    private val authorBooksRepository: AuthorBooksRepository,
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository,
): BookService {

    @Transactional
    override fun insertBook(insertBookRequest: InsertBookRequest) {

        // 書籍登録
        val insertedBookId: Int = bookRepository.insertBook(
            BookDto(
                bookId = null, // 登録時にDBで発番
                title = insertBookRequest.title,
                price = insertBookRequest.price,
                publish = insertBookRequest.publish,
            )
        )

        for (authorId in insertBookRequest.authorIds) {

            // TODO: 指定した著者IDが存在していることの確認


            // 著者・書籍登録
            authorBooksRepository.insertAuthorBooks(
                authorId = authorId,
                bookId = insertedBookId
            )
        }
    }

    @Transactional
    override fun updateBook(bookUpdateRequest: UpdateBookRequest) {

        // TODO: 指定した書籍IDが存在していることの確認

        // 書籍情報更新　
        bookRepository.updateBook(
            BookDto(
                bookId = bookUpdateRequest.id,
                title = bookUpdateRequest.title,
                price = bookUpdateRequest.price,
                publish = bookUpdateRequest.publish,
            )
        )

        // 書籍IDに紐つく書籍・著者情報を削除後に再登録
        authorBooksRepository.deleteAuthorBooks(bookUpdateRequest.id)
        for (authorId in bookUpdateRequest.authorIds) {

            // TODO: 指定した著者IDが存在していることの確認

            authorBooksRepository.insertAuthorBooks(authorId, bookUpdateRequest.id)
        }
    }
}