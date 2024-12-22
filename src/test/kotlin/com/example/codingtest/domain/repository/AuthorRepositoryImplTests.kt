package com.example.codingtest.domain.repository

import com.example.codingtest.config.TestContainersConfiguration
import com.example.codingtest.domain.model.dto.AuthorDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@Import(TestContainersConfiguration::class, AuthorRepositoryImpl::class)
@JooqTest
@Sql("/test-data.sql")
class AuthorRepositoryImplTests {

    @Autowired
    lateinit var authorRepositoryImpl: AuthorRepositoryImpl

    /**
     * AuthorRepositoryImpl.getAuthor テストコード
     */
    @Test
    fun test_getAuthor() {

        // 著者1
        val actual1: AuthorDto = authorRepositoryImpl.getAuthor(1)

        assertEquals(1, actual1.authorId)
        assertEquals("著者1", actual1.name)
        assertEquals("19750101", actual1.birthday)

        // 著者2
        val actual2 : AuthorDto = authorRepositoryImpl.getAuthor(2)

        assertEquals(2, actual2.authorId)
        assertEquals("著者2", actual2.name)
        assertEquals("19741108", actual2.birthday)
    }

    /**
     * AuthorRepositoryImpl.insertAuthor テストコード
     */
    @Test
    fun test_insertAuthor() {
        // 著者3
        authorRepositoryImpl.insertAuthor("著者3", "20001212")

        val actual: AuthorDto = authorRepositoryImpl.getAuthor(3)

        assertEquals(3, actual.authorId)
        assertEquals("著者3", actual.name)
        assertEquals("20001212", actual.birthday)
    }

    /**
     * AuthorRepositoryImpl.updateAuthor テストコード
     */
    @Test
    fun test_updateAuthor() {

        // 著者2
        authorRepositoryImpl.updateAuthor(2, "著者4", "19930101")

        val actual: AuthorDto = authorRepositoryImpl.getAuthor(2)

        assertEquals(2, actual.authorId)
        assertEquals("著者4", actual.name)
        assertEquals("19930101", actual.birthday)
    }
}