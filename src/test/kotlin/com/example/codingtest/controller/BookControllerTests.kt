package com.example.codingtest.controller

import com.example.codingtest.common.exception.InvalidPublishUpdatedException
import com.example.codingtest.common.exception.NotFoundException
import com.example.codingtest.domain.model.request.InsertBookRequest
import com.example.codingtest.domain.model.request.UpdateBookRequest
import com.example.codingtest.domain.service.BookService
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@WebMvcTest(BookController::class)
class BookControllerTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var bookService: BookService

    @Test
    fun test_success_insertBook() {

        val insertBookRequest = InsertBookRequest(
            title = "著者1書籍1巻",
            price = 420,
            publish = "1",
            authorIds = listOf(1,2)
        )

        whenever(bookService.insertBook(insertBookRequest)).then {  }

        val requestBody: String = """
            {
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "1",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk) // 200
    }

    @Test
    fun test_failure_request_name_insertBook() {

        val requestBody: String = """
            {
              "title": "",
              "price": 420,
              "publish": "1",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest) // 400
    }

    @Test
    fun test_failure_request_title_insertBook() {

        val requestBody: String = """
            {
              "title": "著者1書籍1巻",
              "price": -1,
              "publish": "1",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest) // 400
    }

    @Test
    fun test_failure_request_publish_insertBook() {

        val requestBody1: String = """
            {
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody1))
            .andExpect(status().isBadRequest) // 400

        val requestBody2: String = """
            {
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "10",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody2))
            .andExpect(status().isBadRequest) // 400
    }

    @Test
    fun test_failure_request_authorIds_insertBook() {

        val requestBody: String = """
            {
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "1",
              "authorIds": []
            }
        """.trimIndent()

        mockMvc.perform(post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest) // 400
    }

    @Test
    fun test_failure_NotFoundException_insertBook() {

        val insertBookRequest = InsertBookRequest(
            title = "著者1書籍1巻",
            price = 420,
            publish = "1",
            authorIds = listOf(1,2)
        )

        whenever(bookService.insertBook(insertBookRequest)).thenThrow(NotFoundException::class.java)

        val requestBody: String = """
            {
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "1",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isNotFound) // 404
    }

    @Test
    fun test_failure_RuntimeException_insertBook() {

        val insertBookRequest = InsertBookRequest(
            title = "著者1書籍1巻",
            price = 420,
            publish = "1",
            authorIds = listOf(1,2)
        )

        whenever(bookService.insertBook(insertBookRequest)).thenThrow(RuntimeException::class.java)

        val requestBody: String = """
            {
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "1",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(post("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isInternalServerError) // 500
    }

    @Test
    fun test_success_updateBook() {

        val updateBookRequest = UpdateBookRequest(
            id = 1,
            title = "著者1書籍1巻",
            price = 420,
            publish = "1",
            authorIds = listOf(1,2)
        )

        whenever(bookService.updateBook(updateBookRequest)).then {  }

        val requestBody: String = """
            {
              "id": 1,
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "1",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(patch("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk) // 200
    }

    @Test
    fun test_failure_request_id_updateBook() {

        val requestBody: String = """
            {
              "id": 0,
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "1",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(patch("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest) // 400
    }

    @Test
    fun test_failure_request_title_updateBook() {

        val requestBody: String = """
            {
              "id": 1,
              "title": "",
              "price": 420,
              "publish": "1",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(patch("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest) // 400

    }

    @Test
    fun test_failure_request_price_updateBook() {

        val requestBody: String = """
            {
              "id": 1,
              "title": "著者1書籍1巻",
              "price": -1,
              "publish": "1",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(patch("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest) // 400
    }

    @Test
    fun test_failure_request_publish_updateBook() {

        val requestBody1: String = """
            {
              "id": 1,
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(patch("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody1))
            .andExpect(status().isBadRequest) // 400

        val requestBody2: String = """
            {
              "id": 1,
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "10",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(patch("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody2))
            .andExpect(status().isBadRequest) // 400
    }

    @Test
    fun test_failure_NotFoundException_updateBook() {

        val updateBookRequest = UpdateBookRequest(
            id = 1,
            title = "著者1書籍1巻",
            price = 420,
            publish = "1",
            authorIds = listOf(1,2)
        )

        whenever(bookService.updateBook(updateBookRequest)).thenThrow(NotFoundException::class.java)

        val requestBody: String = """
            {
              "id": 1,
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "1",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(patch("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isNotFound) // 404
    }

    @Test
    fun test_failure_InvalidPublishUpdatedException_updateBook() {

        val updateBookRequest = UpdateBookRequest(
            id = 1,
            title = "著者1書籍1巻",
            price = 420,
            publish = "1",
            authorIds = listOf(1,2)
        )

        whenever(bookService.updateBook(updateBookRequest)).thenThrow(InvalidPublishUpdatedException::class.java)

        val requestBody: String = """
            {
              "id": 1,
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "1",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(patch("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest) // 400
    }

    @Test
    fun test_failure_RuntimeException_updateBook() {

        val updateBookRequest = UpdateBookRequest(
            id = 1,
            title = "著者1書籍1巻",
            price = 420,
            publish = "1",
            authorIds = listOf(1,2)
        )

        whenever(bookService.updateBook(updateBookRequest)).thenThrow(RuntimeException::class.java)

        val requestBody: String = """
            {
              "id": 1,
              "title": "著者1書籍1巻",
              "price": 420,
              "publish": "1",
              "authorIds": [1,2]
            }
        """.trimIndent()

        mockMvc.perform(patch("/api/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isInternalServerError) // 500
    }

}