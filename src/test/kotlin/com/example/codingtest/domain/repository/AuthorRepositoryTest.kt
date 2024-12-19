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
}