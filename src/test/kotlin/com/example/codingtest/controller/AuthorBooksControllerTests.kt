package com.example.codingtest.controller

import com.example.codingtest.domain.model.dto.AuthorBooksDto
import com.example.codingtest.domain.service.AuthorBooksService
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@WebMvcTest(AuthorBooksController::class)
class AuthorBooksControllerTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var authorBooksService: AuthorBooksService

    /**
     * AuthorBooksController.getAuthorBooks 正常系
     */
    @Test
    fun test_success_getAuthorBooks() {

        whenever(authorBooksService.getAuthorBooks()).thenReturn(
            listOf(
                AuthorBooksDto(
                    authorId = 1,
                    name = "著者1",
                    birthday = "19750101",
                    bookId = 1,
                    title = "著者1書籍1巻",
                    price = 420,
                    publish = "1",
                ),
                AuthorBooksDto(
                    authorId = 2,
                    name = "著者2",
                    birthday = "19741108",
                    bookId = 3,
                    title = "著者2書籍1巻",
                    price = 430,
                    publish = "1",
                )
            )
        )

        mockMvc.perform(get("/api/author-books-ids").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk) // 200
            .andExpect(jsonPath("$.[0].authorId").value(1))
            .andExpect(jsonPath("$.[0].name").value("著者1"))
            .andExpect(jsonPath("$.[0].birthday").value("19750101"))
            .andExpect(jsonPath("$.[0].bookId").value(1))
            .andExpect(jsonPath("$.[0].title").value("著者1書籍1巻"))
            .andExpect(jsonPath("$.[0].price").value(420))
            .andExpect(jsonPath("$.[0].publish").value("1"))
            .andExpect(jsonPath("$.[1].authorId").value(2))
            .andExpect(jsonPath("$.[1].name").value("著者2"))
            .andExpect(jsonPath("$.[1].birthday").value("19741108"))
            .andExpect(jsonPath("$.[1].bookId").value(3))
            .andExpect(jsonPath("$.[1].title").value("著者2書籍1巻"))
            .andExpect(jsonPath("$.[1].price").value(430))
            .andExpect(jsonPath("$.[1].publish").value("1"))
    }

    /**
     * AuthorBooksController.getAuthorBooks 異常系 RuntimeException
     */
    @Test
    fun test_failure_RuntimeException_getAuthorBooks() {

        whenever(authorBooksService.getAuthorBooks()).thenThrow(RuntimeException::class.java)

        mockMvc.perform(get("/api/author-books-ids").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError) // 500
    }
}
