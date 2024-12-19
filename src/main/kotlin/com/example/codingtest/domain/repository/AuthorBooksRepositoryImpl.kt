package com.example.codingtest.domain.repository

import com.example.codingtest.domain.tables.Author
import com.example.codingtest.domain.tables.AuthorBooks
import com.example.codingtest.domain.tables.Book
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class AuthorBooksRepositoryImpl: AuthorBooksRepository {

    @Autowired
    lateinit var dslContext: DSLContext

    override fun getAuthorBooks(): List<Record> {
        return dslContext.select(
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
            .on(AuthorBooks.AUTHOR_BOOKS.AUTHOR_ID.eq(Author.AUTHOR.ID))
            .leftJoin(Book.BOOK)
            .on(AuthorBooks.AUTHOR_BOOKS.BOOK_ID.eq(Book.BOOK.ID))
            .orderBy(AuthorBooks.AUTHOR_BOOKS.AUTHOR_ID, AuthorBooks.AUTHOR_BOOKS.BOOK_ID)
            .toList()
    }

    override fun getBookIds(authorId: Int): List<Record>{
        return dslContext.select(AuthorBooks.AUTHOR_BOOKS.BOOK_ID)
            .from(AuthorBooks.AUTHOR_BOOKS)
            .where(AuthorBooks.AUTHOR_BOOKS.AUTHOR_ID.eq(authorId))
            .toList()
    }

    override fun insertAuthorBooks(authorId: Int, bookId: Int) {
        dslContext.insertInto(AuthorBooks.AUTHOR_BOOKS,
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