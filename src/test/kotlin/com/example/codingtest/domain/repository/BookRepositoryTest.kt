package com.example.codingtest.domain.repository

import com.example.codingtest.config.TestContainersConfiguration
import com.example.codingtest.domain.exception.CannotUpdatedException
import com.example.codingtest.domain.tables.Book
import org.jooq.Record
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

@Import(TestContainersConfiguration::class, BookRepositoryImpl::class)
@JooqTest
@Sql("/test-data.sql")
class BookRepositoryTest {

    @Autowired
    lateinit var bookRepositoryImpl: BookRepositoryImpl

    /**
     * BookRepositoryImpl.getBooks テストコード
     */
    @Test
    fun getBooksTest() {

        val actual: List<Record> = bookRepositoryImpl.getBooks(listOf(1, 2, 3))

        // 書籍1
        assertEquals(1, actual[0].getValue(Book.BOOK.ID))
        assertEquals("著者1書籍1巻", actual[0].getValue(Book.BOOK.TITLE))
        assertEquals(420, actual[0].getValue(Book.BOOK.PRICE))
        assertEquals("1", actual[0].getValue(Book.BOOK.PUBLISH))

        // 書籍2
        assertEquals(2, actual[1].getValue(Book.BOOK.ID))
        assertEquals("著者1書籍2巻", actual[1].getValue(Book.BOOK.TITLE))
        assertEquals(425, actual[1].getValue(Book.BOOK.PRICE))
        assertEquals("0", actual[1].getValue(Book.BOOK.PUBLISH))

        // 書籍3
        assertEquals(3, actual[2].getValue(Book.BOOK.ID))
        assertEquals("著者2書籍1巻", actual[2].getValue(Book.BOOK.TITLE))
        assertEquals(430, actual[2].getValue(Book.BOOK.PRICE))
        assertEquals("1", actual[2].getValue(Book.BOOK.PUBLISH))
    }

    /**
     * BookRepositoryImpl.insertBook テストコード
     */
    @Test
    fun insertBookTest() {

        val book = com.example.codingtest.domain.model.Book(
            bookId = 0,
            title = "著者1.2書籍1",
            price = 500,
            "0"
        )

        // 自動発番したbookIdを取得
        val actual1: Int = bookRepositoryImpl.insertBook(book)

        assertEquals(4, actual1)

        // 登録した書籍
        val actual2: List<Record> = bookRepositoryImpl.getBooks(listOf(4))

        assertEquals(4, actual2[0].getValue(Book.BOOK.ID))
        assertEquals("著者1.2書籍1", actual2[0].getValue(Book.BOOK.TITLE))
        assertEquals(500, actual2[0].getValue(Book.BOOK.PRICE))
        assertEquals("0", actual2[0].getValue(Book.BOOK.PUBLISH))
    }

    /**
     * BookRepositoryImpl.updateBook テストコード
     */
    @Test
    fun updateBookTest() {

        // 出版状況を 出版済→未出版に更新(エラー)
        assertThrows<CannotUpdatedException> {
            // 出版状況を 未出版→出版済に更新
            val book1 = com.example.codingtest.domain.model.Book(
                bookId = 1,
                title = "著者1書籍6",
                price = 1000,
                "0"
            )
            bookRepositoryImpl.updateBook(book1)
        }

        // 出版状況を 未出版→出版済に更新
        val book2 = com.example.codingtest.domain.model.Book(
            bookId = 2,
            title = "著者1書籍5",
            price = 900,
            "1"
        )
        bookRepositoryImpl.updateBook(book2)

        // 更新した書籍確認
        val actual: List<Record> = bookRepositoryImpl.getBooks(listOf(1, 2))

        // 出版状況を 出版済→未出版に更新 エラー発生のためロールバックされていること
        assertEquals(1, actual[0].getValue(Book.BOOK.ID))
        assertEquals("著者1書籍1巻", actual[0].getValue(Book.BOOK.TITLE))
        assertEquals(420, actual[0].getValue(Book.BOOK.PRICE))
        assertEquals("1", actual[0].getValue(Book.BOOK.PUBLISH))

        // 出版状況を 未出版→出版済に更新 更新されていること
        assertEquals(2, actual[1].getValue(Book.BOOK.ID))
        assertEquals("著者1書籍5", actual[1].getValue(Book.BOOK.TITLE))
        assertEquals(900, actual[1].getValue(Book.BOOK.PRICE))
        assertEquals("1", actual[1].getValue(Book.BOOK.PUBLISH))
    }
}