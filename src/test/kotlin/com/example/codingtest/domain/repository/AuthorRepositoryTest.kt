package com.example.codingtest.domain.repository

import com.example.codingtest.config.TestContainersConfiguration
import com.example.codingtest.domain.tables.Author
import org.jooq.Record
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@Import(TestContainersConfiguration::class, AuthorRepositoryImpl::class)
@JooqTest
@Sql("/test-data.sql")
class AuthorRepositoryTest {

    @Autowired
    lateinit var authorRepositoryImpl: AuthorRepositoryImpl

    /**
     * AuthorRepositoryImpl.getAuthor テストコード
     */
    @Test
    fun getAuthorTest() {

        // 著者1
        val actual1: Record = authorRepositoryImpl.getAuthor(1)

        assertEquals(1, actual1.getValue(Author.AUTHOR.ID))
        assertEquals("著者1", actual1.getValue(Author.AUTHOR.NAME))
        assertEquals("19750101", actual1.getValue(Author.AUTHOR.BIRTHDAY))

        // 著者2
        val actual2 : Record = authorRepositoryImpl.getAuthor(2)

        assertEquals(2, actual2.getValue(Author.AUTHOR.ID))
        assertEquals("著者2", actual2.getValue(Author.AUTHOR.NAME))
        assertEquals("19741108", actual2.getValue(Author.AUTHOR.BIRTHDAY))
    }

    /**
     * AuthorRepositoryImpl.insertAuthor テストコード
     */
    @Test
    fun insertAuthorTest() {
        // 著者3
        authorRepositoryImpl.insertAuthor("著者3", "20001212")

        val actual: Record = authorRepositoryImpl.getAuthor(3)

        assertEquals(3, actual.getValue(Author.AUTHOR.ID))
        assertEquals("著者3", actual.getValue(Author.AUTHOR.NAME))
        assertEquals("20001212", actual.getValue(Author.AUTHOR.BIRTHDAY))
    }

    /**
     * AuthorRepositoryImpl.updateAuthor テストコード
     */
    @Test
    fun updateAuthorTest() {

        // 著者2
        authorRepositoryImpl.updateAuthor(2, "著者4", "19930101")

        val actual: Record = authorRepositoryImpl.getAuthor(2)

        assertEquals(2, actual.getValue(Author.AUTHOR.ID))
        assertEquals("著者4", actual.getValue(Author.AUTHOR.NAME))
        assertEquals("19930101", actual.getValue(Author.AUTHOR.BIRTHDAY))
    }
}