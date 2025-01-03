package com.example.codingtest.domain.repository

import com.example.codingtest.config.TestContainersConfiguration
import com.example.codingtest.common.exception.InvalidPublishUpdatedException
import com.example.codingtest.common.exception.NotFoundException
import com.example.codingtest.domain.model.dto.BookDto
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
class BookRepositoryImplTests {

    @Autowired
    lateinit var bookRepositoryImpl: BookRepositoryImpl

    /**
     * BookRepositoryImpl.getBooks 正常系
     */
    @Test
    fun test_success_getBooks() {

        val actual: List<BookDto> = bookRepositoryImpl.getBooks(listOf(1, 2, 3))

        // 書籍1
        assertEquals(1, actual[0].bookId)
        assertEquals("著者1書籍1巻", actual[0].title)
        assertEquals(420, actual[0].price)
        assertEquals("1", actual[0].publish)

        // 書籍2
        assertEquals(2, actual[1].bookId)
        assertEquals("著者1書籍2巻", actual[1].title)
        assertEquals(425, actual[1].price)
        assertEquals("0", actual[1].publish)

        // 書籍3
        assertEquals(3, actual[2].bookId)
        assertEquals("著者2書籍1巻", actual[2].title)
        assertEquals(0, actual[2].price)
        assertEquals("1", actual[2].publish)
    }

    /**
     * BookRepositoryImpl.insertBook 正常系
     */
    @Test
    fun test_success_insertBook() {

        val bookDto = BookDto(
            bookId = 0,
            title = "著者1.2書籍1",
            price = 500,
            "0"
        )

        // 自動発番したbookIdを取得
        val actual1: Int = bookRepositoryImpl.insertBook(bookDto)

        assertEquals(4, actual1)

        // 登録した書籍
        val actual2: List<BookDto> = bookRepositoryImpl.getBooks(listOf(4))

        assertEquals(4, actual2[0].bookId)
        assertEquals("著者1.2書籍1", actual2[0].title)
        assertEquals(500, actual2[0].price)
        assertEquals("0", actual2[0].publish)
    }

    /**
     * BookRepositoryImpl.updateBook 正常系
     */
    @Test
    fun test_success_updateBook() {

        // 出版状況を 未出版→出版済に更新
        val bookDto = BookDto(
            bookId = 2,
            title = "著者1書籍5",
            price = 900,
            "1"
        )
        bookRepositoryImpl.updateBook(bookDto)

        // 更新した書籍確認
        val actual: List<BookDto> = bookRepositoryImpl.getBooks(listOf(2))

        // 出版状況を 未出版→出版済に更新 更新されていること
        assertEquals(2, actual[0].bookId)
        assertEquals("著者1書籍5", actual[0].title)
        assertEquals(900, actual[0].price)
        assertEquals("1", actual[0].publish)
    }

    /**
     * BookRepositoryImpl.updateBook 異常系 InvalidPublishUpdatedException
     */
    @Test
    fun test_failure_InvalidPublishUpdatedException_updateBook() {

        // 出版状況を 出版済→未出版に更新(エラー)
        assertThrows<InvalidPublishUpdatedException> {
            // 出版状況を 未出版→出版済に更新
            val bookDto = BookDto(
                bookId = 1,
                title = "著者1書籍6",
                price = 1000,
                "0"
            )
            bookRepositoryImpl.updateBook(bookDto)
        }

        // 更新した書籍確認
        val actual: List<BookDto> = bookRepositoryImpl.getBooks(listOf(1))

        // 出版状況を 出版済→未出版に更新 エラー発生のためロールバックされていること
        assertEquals(1, actual[0].bookId)
        assertEquals("著者1書籍1巻", actual[0].title)
        assertEquals(420, actual[0].price)
        assertEquals("1", actual[0].publish)
    }

    /**
     * BookRepositoryImpl.updateBook 異常系 NotFoundException
     */
    @Test
    fun test_failure_NotFoundException_updateBook() {

        // 出版状況を 出版済→未出版に更新(エラー)
        assertThrows<NotFoundException> {
            // 出版状況を 未出版→出版済に更新
            val bookDto = BookDto(
                bookId = 999,
                title = "著者999書籍6",
                price = 9999,
                "1"
            )
            bookRepositoryImpl.updateBook(bookDto)
        }
    }
}
