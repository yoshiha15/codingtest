package com.example.codingtest.domain.repository

import com.example.codingtest.config.TestContainersConfiguration
import com.example.codingtest.domain.tables.Author
import com.example.codingtest.domain.tables.AuthorBooks
import com.example.codingtest.domain.tables.Book
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@Import(TestContainersConfiguration::class, AuthorBooksRepositoryImpl::class)
@JooqTest
@Sql("/test-data.sql")
class AuthorBooksRepositoryTest {

    @Autowired
    lateinit var authorBooksRepositoryImpl: AuthorBooksRepositoryImpl

    /**
     * AuthorBooksRepositoryImpl.getAuthorBooks テストコード
     */
    @Test
    fun getAuthorBooksTest() {

        val actual: List<Record> = authorBooksRepositoryImpl.getAuthorBooks()

        // 件数
        assertEquals(3, actual.size)

        // 著者1
        assertEquals(1, actual[0].getValue(AuthorBooks.AUTHOR_BOOKS.AUTHOR_ID))
        assertEquals(1, actual[0].getValue(AuthorBooks.AUTHOR_BOOKS.BOOK_ID))
        assertEquals("著者1", actual[0].getValue(Author.AUTHOR.NAME))
        assertEquals("19750101", actual[0].getValue(Author.AUTHOR.BIRTHDAY))
        assertEquals("著者1書籍1巻", actual[0].getValue(Book.BOOK.TITLE))
        assertEquals(420, actual[0].getValue(Book.BOOK.PRICE))
        assertEquals("1", actual[0].getValue(Book.BOOK.PUBLISH))

        assertEquals(1, actual[1].getValue(AuthorBooks.AUTHOR_BOOKS.AUTHOR_ID))
        assertEquals(2, actual[1].getValue(AuthorBooks.AUTHOR_BOOKS.BOOK_ID))
        assertEquals("著者1", actual[1].getValue(Author.AUTHOR.NAME))
        assertEquals("19750101", actual[1].getValue(Author.AUTHOR.BIRTHDAY))
        assertEquals("著者1書籍2巻", actual[1].getValue(Book.BOOK.TITLE))
        assertEquals(425, actual[1].getValue(Book.BOOK.PRICE))
        assertEquals("0", actual[1].getValue(Book.BOOK.PUBLISH))

        // 著者2
        assertEquals(2, actual[2].getValue(AuthorBooks.AUTHOR_BOOKS.AUTHOR_ID))
        assertEquals(3, actual[2].getValue(AuthorBooks.AUTHOR_BOOKS.BOOK_ID))
        assertEquals("著者2", actual[2].getValue(Author.AUTHOR.NAME))
        assertEquals("19741108", actual[2].getValue(Author.AUTHOR.BIRTHDAY))
        assertEquals("著者2書籍1巻", actual[2].getValue(Book.BOOK.TITLE))
        assertEquals(430, actual[2].getValue(Book.BOOK.PRICE))
        assertEquals("1", actual[2].getValue(Book.BOOK.PUBLISH))
    }

    /**
     * AuthorBooksRepositoryImpl.getBookIds テストコード
     */
    @Test
    fun getBookIds() {

        val actual: List<Record> = authorBooksRepositoryImpl.getBookIds(1)

        // 件数
        assertEquals(2, actual.size)

        // 書籍ID
        assertEquals(1, actual[0].getValue(AuthorBooks.AUTHOR_BOOKS.BOOK_ID))
        assertEquals(2, actual[1].getValue(AuthorBooks.AUTHOR_BOOKS.BOOK_ID))
    }

}