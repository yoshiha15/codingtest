package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.dto.BookDto
import com.example.codingtest.domain.model.request.InsertBookRequest
import com.example.codingtest.domain.model.request.UpdateBookRequest
import com.example.codingtest.domain.repository.AuthorBooksRepository
import com.example.codingtest.domain.repository.AuthorRepository
import com.example.codingtest.domain.repository.BookRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Service

/**
 * BookServiceImpl
 */
@Service
class BookServiceImpl(
    private val authorBooksRepository: AuthorBooksRepository,
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository,
    private val dslContext: DSLContext,
): BookService {

    override fun insertBook(insertBookRequest: InsertBookRequest) {

        dslContext.transaction { _ ->

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

                // 指定した著者IDが存在していることのチェック 存在していない場合はNotFoundExceptionをthrow
                authorRepository.getAuthor(authorId)

                // 著者・書籍登録
                authorBooksRepository.insertAuthorBooks(
                    authorId = authorId,
                    bookId = insertedBookId
                )
            }
        }
    }

    override fun updateBook(bookUpdateRequest: UpdateBookRequest) {

        dslContext.transaction { _ ->
            // 書籍情報更新　
            bookRepository.updateBook(
                BookDto(
                    bookId = bookUpdateRequest.bookId,
                    title = bookUpdateRequest.title,
                    price = bookUpdateRequest.price,
                    publish = bookUpdateRequest.publish,
                )
            )

            // 書籍IDに紐つく書籍・著者情報を削除後に再登録
            authorBooksRepository.deleteAuthorBooks(bookUpdateRequest.bookId)
            for (authorId in bookUpdateRequest.authorIds) {

                // 指定した著者IDが存在していることのチェック 存在していない場合はNotFoundExceptionをthrow
                authorRepository.getAuthor(authorId)

                authorBooksRepository.insertAuthorBooks(authorId, bookUpdateRequest.bookId)
            }
        }
    }
}