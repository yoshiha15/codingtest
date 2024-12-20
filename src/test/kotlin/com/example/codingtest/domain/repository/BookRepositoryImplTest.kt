package com.example.codingtest.domain.repository

import com.example.codingtest.config.TestContainersConfiguration
import com.example.codingtest.domain.exception.InvalidPublishUpdatedException
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
class BookRepositoryImplTest {

    @Autowired
    lateinit var bookRepositoryImpl: BookRepositoryImpl

    /**
     * BookRepositoryImpl.getBooks テストコード
     */
    @Test
    fun test_getBooks() {

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
        assertEquals(430, actual[2].price)
        assertEquals("1", actual[2].publish)
    }

    /**
     * BookRepositoryImpl.insertBook テストコード
     */
    @Test
    fun test_insertBook() {

        val bookDto = com.example.codingtest.domain.model.dto.BookDto(
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
     * BookRepositoryImpl.updateBook テストコード
     */
    @Test
    fun test_updateBook() {

        // 出版状況を 出版済→未出版に更新(エラー)
        assertThrows<InvalidPublishUpdatedException> {
            // 出版状況を 未出版→出版済に更新
            val bookDto1 = com.example.codingtest.domain.model.dto.BookDto(
                bookId = 1,
                title = "著者1書籍6",
                price = 1000,
                "0"
            )
            bookRepositoryImpl.updateBook(bookDto1)
        }

        // 出版状況を 未出版→出版済に更新
        val bookDto2 = com.example.codingtest.domain.model.dto.BookDto(
            bookId = 2,
            title = "著者1書籍5",
            price = 900,
            "1"
        )
        bookRepositoryImpl.updateBook(bookDto2)

        // 更新した書籍確認
        val actual: List<BookDto> = bookRepositoryImpl.getBooks(listOf(1, 2))

        // 出版状況を 出版済→未出版に更新 エラー発生のためロールバックされていること
        assertEquals(1, actual[0].bookId)
        assertEquals("著者1書籍1巻", actual[0].title)
        assertEquals(420, actual[0].price)
        assertEquals("1", actual[0].publish)

        // 出版状況を 未出版→出版済に更新 更新されていること
        assertEquals(2, actual[1].bookId)
        assertEquals("著者1書籍5", actual[1].title)
        assertEquals(900, actual[1].price)
        assertEquals("1", actual[1].publish)
    }
}