package com.example.codingtest.domain.service

import com.example.codingtest.domain.model.dto.AuthorDto
import com.example.codingtest.domain.model.dto.BookDto
import com.example.codingtest.domain.model.response.GetAuthorResponse
import com.example.codingtest.domain.repository.AuthorBooksRepository
import com.example.codingtest.domain.repository.AuthorRepository
import com.example.codingtest.domain.repository.BookRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class AuthorServiceImplTests{

    @InjectMocks
    lateinit var authorServiceImpl: AuthorServiceImpl

    @Mock
    lateinit var authorRepository: AuthorRepository

    @Mock
    lateinit var authorBooksRepository: AuthorBooksRepository

    @Mock
    lateinit var bookRepository: BookRepository

    /**
     * authorServiceImpl.getAuthor 正常系
     */
    @Test
    fun test_success_getAuthor() {

        whenever(authorRepository.getAuthor(1)).thenReturn(
            AuthorDto(
                authorId = 1,
                name = "著者1",
                birthday = "19750101",
            )
        )

        whenever(authorBooksRepository.getBookIds(1)).thenReturn(
            listOf(1,2)
        )

        whenever(bookRepository.getBooks(listOf(1,2))).thenReturn(
            listOf(
                BookDto(
                    bookId = 1,
                    title = "著者1書籍1巻",
                    price = 420,
                    publish = "1"
                ),
                BookDto(
                    bookId = 2,
                    title = "著者1書籍2巻",
                    price = 425,
                    publish = "0"
                ),
            )
        )

        val actual: GetAuthorResponse = authorServiceImpl.getAuthor(1)

        assertEquals(1, actual.authorId)
        assertEquals("著者1", actual.name)
        assertEquals("19750101", actual.birthday)

        assertEquals(1, actual.books[0].bookId)
        assertEquals("著者1書籍1巻", actual.books[0].title)
        assertEquals(420, actual.books[0].price)
        assertEquals("1", actual.books[0].publish)

        assertEquals(2, actual.books[1].bookId)
        assertEquals("著者1書籍2巻", actual.books[1].title)
        assertEquals(425, actual.books[1].price)
        assertEquals("0", actual.books[1].publish)
    }
}