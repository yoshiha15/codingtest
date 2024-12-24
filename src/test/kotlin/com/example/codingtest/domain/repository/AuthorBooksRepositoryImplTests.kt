package com.example.codingtest.domain.repository

import com.example.codingtest.config.TestContainersConfiguration
import com.example.codingtest.domain.model.dto.AuthorBooksDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@Import(TestContainersConfiguration::class, AuthorBooksRepositoryImpl::class)
@JooqTest
@Sql("/test-data.sql")
class AuthorBooksRepositoryImplTests {

    @Autowired
    lateinit var authorBooksRepositoryImpl: AuthorBooksRepositoryImpl

    /**
     * AuthorBooksRepositoryImpl.getAuthorBooks 正常系
     */
    @Test
    fun test_success_getAuthorBooks() {

        val actual: List<AuthorBooksDto> = authorBooksRepositoryImpl.getAuthorBooks()

        // 件数
        assertEquals(3, actual.size)

        // 著者1
        assertEquals(1, actual[0].authorId)
        assertEquals(1, actual[0].bookId)
        assertEquals("著者1", actual[0].name)
        assertEquals("19750101", actual[0].birthday)
        assertEquals("著者1書籍1巻", actual[0].title)
        assertEquals(420, actual[0].price)
        assertEquals("1", actual[0].publish)

        assertEquals(1, actual[1].authorId)
        assertEquals(2, actual[1].bookId)
        assertEquals("著者1", actual[1].name)
        assertEquals("19750101", actual[1].birthday)
        assertEquals("著者1書籍2巻", actual[1].title)
        assertEquals(425, actual[1].price)
        assertEquals("0", actual[1].publish)

        // 著者2
        assertEquals(2, actual[2].authorId)
        assertEquals(3, actual[2].bookId)
        assertEquals("著者2", actual[2].name)
        assertEquals("19741108", actual[2].birthday)
        assertEquals("著者2書籍1巻", actual[2].title)
        assertEquals(0, actual[2].price)
        assertEquals("1", actual[2].publish)
    }

    /**
     * AuthorBooksRepositoryImpl.getBookIds 正常系
     */
    @Test
    fun test_success_getBookIds() {

        val actual: List<Int> = authorBooksRepositoryImpl.getBookIds(1)

        // 件数
        assertEquals(2, actual.size)

        // 書籍ID
        assertEquals(1, actual[0])
        assertEquals(2, actual[1])
    }

    /**
     * AuthorBooksRepositoryImpl.insertAuthorBooks 正常系
     */
    @Test
    fun test_success_insertAuthorBooks() {

        authorBooksRepositoryImpl.insertAuthorBooks(1, 4)
    }

    /**
     * AuthorBooksRepositoryImpl.deleteAuthorBooks 正常系
     */
    @Test
    fun test_success_deleteAuthorBooks() {

        authorBooksRepositoryImpl.deleteAuthorBooks(1)

        val actual1: List<Int> = authorBooksRepositoryImpl.getBookIds(1)

        // 件数
        assertEquals(1, actual1.size)
        // 書籍ID
        assertEquals(2, actual1[0])
    }
}
