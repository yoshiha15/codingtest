package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.dto.AuthorBooksDto
import com.example.codingtest.domain.repository.AuthorBooksRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class AuthorBooksServiceImplTest {

    @InjectMocks
    lateinit var authorBooksServiceImpl: AuthorBooksServiceImpl

    @Mock
    lateinit var authorBooksRepository: AuthorBooksRepository

    /**
     * authorBooksServiceImpl.getAuthorBooks テストコード
     */
    @Test
    fun test_getAuthorBooks() {

        // スタブ登録
        whenever(authorBooksRepository.getAuthorBooks()).thenReturn(
            listOf(
                AuthorBooksDto(
                    authorId = 1,
                    name = "著者1",
                    birthday = "19750101",
                    bookId = 1,
                    title = "著者1書籍1",
                    price = 420,
                    publish = "1"
                )
            )
        )

        val actual: List<AuthorBooksDto> = authorBooksServiceImpl.getAuthorBooks()

        assertEquals(1, actual[0].authorId)
        assertEquals("著者1", actual[0].name)
        assertEquals("19750101", actual[0].birthday)
        assertEquals(1, actual[0].bookId)
        assertEquals("著者1書籍1", actual[0].title)
        assertEquals(420, actual[0].price)
        assertEquals("1", actual[0].publish)
    }

}