package com.example.codingtest.domain.repository

import com.example.codingtest.domain.model.dto.AuthorBooksDto
import com.example.codingtest.domain.jooq.tables.Author
import com.example.codingtest.domain.jooq.tables.AuthorBooks
import com.example.codingtest.domain.jooq.tables.Book
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository

/**
 * AuthorBooksRepositoryImpl
 */
@Repository
class AuthorBooksRepositoryImpl(
    private val dslContext: DSLContext
): AuthorBooksRepository {

    override fun getAuthorBooks(): List<AuthorBooksDto> {
        val result: List<Record> =dslContext.select(
            AuthorBooks.AUTHOR_BOOKS.AUTHOR_ID,
            AuthorBooks.AUTHOR_BOOKS.BOOK_ID,
            Author.AUTHOR.NAME,
            Author.AUTHOR.BIRTHDAY,
            Book.BOOK.TITLE,
            Book.BOOK.PRICE,
            Book.BOOK.PUBLISH
        )
            .from(AuthorBooks.AUTHOR_BOOKS)
            .leftJoin(Author.AUTHOR)
            .on(
                AuthorBooks.AUTHOR_BOOKS.AUTHOR_ID.eq(
                    Author.AUTHOR.ID))
            .leftJoin(Book.BOOK)
            .on(
                AuthorBooks.AUTHOR_BOOKS.BOOK_ID.eq(
                    Book.BOOK.ID))
            .orderBy(AuthorBooks.AUTHOR_BOOKS.AUTHOR_ID, AuthorBooks.AUTHOR_BOOKS.BOOK_ID)
            .toList()

        return result.map {
            AuthorBooksDto(
                authorId = it.getValue(AuthorBooks.AUTHOR_BOOKS.AUTHOR_ID),
                name = it.getValue(Author.AUTHOR.NAME),
                birthday = it.getValue(Author.AUTHOR.BIRTHDAY),
                bookId = it.getValue(AuthorBooks.AUTHOR_BOOKS.BOOK_ID),
                title = it.getValue(Book.BOOK.TITLE),
                price = it.getValue(Book.BOOK.PRICE),
                publish = it.getValue(Book.BOOK.PUBLISH)
            )
        }


    }

    override fun getBookIds(authorId: Int): List<Int>{
        return dslContext.select(AuthorBooks.AUTHOR_BOOKS.BOOK_ID)
            .from(AuthorBooks.AUTHOR_BOOKS)
            .where(AuthorBooks.AUTHOR_BOOKS.AUTHOR_ID.eq(authorId))
            .toList()
            .map {  it.getValue(AuthorBooks.AUTHOR_BOOKS.BOOK_ID) }
    }

    override fun insertAuthorBooks(authorId: Int, bookId: Int) {
        dslContext.insertInto(
            AuthorBooks.AUTHOR_BOOKS,
            AuthorBooks.AUTHOR_BOOKS.AUTHOR_ID,
            AuthorBooks.AUTHOR_BOOKS.BOOK_ID,
            ).values(authorId, bookId)
            .execute()
    }

    override fun deleteAuthorBooks(bookId: Int){
        dslContext.delete(AuthorBooks.AUTHOR_BOOKS)
            .where(AuthorBooks.AUTHOR_BOOKS.BOOK_ID.eq(bookId))
            .execute()
    }
}
