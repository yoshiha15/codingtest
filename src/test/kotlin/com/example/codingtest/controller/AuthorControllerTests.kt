package com.example.codingtest.controller

import com.example.codingtest.common.exception.NotFoundException
import com.example.codingtest.domain.model.dto.BookDto
import com.example.codingtest.domain.model.request.InsertAuthorRequest
import com.example.codingtest.domain.model.request.UpdateAuthorRequest
import com.example.codingtest.domain.model.response.GetAuthorResponse
import com.example.codingtest.domain.service.AuthorService
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

@WebMvcTest(AuthorController::class)
class AuthorControllerTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var authorService: AuthorService

    /**
     * AuthorController.getAuthor 正常系
     */
    @Test
    fun test_success_getAuthor() {

        whenever(authorService.getAuthor(1)).thenReturn(
            GetAuthorResponse(
                authorId = 1,
                name = "著者1",
                birthday = "19750101",
                books = listOf(
                    BookDto(
                        bookId = 1,
                        title = "著者1書籍1巻",
                        price = 420,
                        publish = "1"
                    ),
                    BookDto(
                        bookId = 2,
                        title = "著者1書籍2巻",
                        price = 0,
                        publish = "0"
                    )
                )
            )
        )

        mockMvc.perform(get("/api/author/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk) // 200
            .andExpect(jsonPath("$.authorId").value(1))
            .andExpect(jsonPath("$.name").value("著者1"))
            .andExpect(jsonPath("$.birthday").value("19750101"))
            .andExpect(jsonPath("$.books[0].bookId").value(1))
            .andExpect(jsonPath("$.books[0].title").value("著者1書籍1巻"))
            .andExpect(jsonPath("$.books[0].price").value(420))
            .andExpect(jsonPath("$.books[0].publish").value("1"))
            .andExpect(jsonPath("$.books[1].bookId").value(2))
            .andExpect(jsonPath("$.books[1].title").value("著者1書籍2巻"))
            .andExpect(jsonPath("$.books[1].price").value(0))
            .andExpect(jsonPath("$.books[1].publish").value("0"))
    }

    /**
     * AuthorController.getAuthor 異常系　pathParameter不正
     */
    @Test
    fun test_failure_request_pathParameter_getAuthor() {

        mockMvc.perform(get("/api/author/0").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest) // 400
    }

    /**
     * AuthorController.getAuthor 異常系　InvalidPathParameter
     */
    @Test
    fun test_failure_NotFoundException_getAuthor() {

        whenever(authorService.getAuthor(1)).thenThrow(NotFoundException::class.java)

        mockMvc.perform(get("/api/author/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound) // 404
    }

    /**
     * AuthorController.getAuthor 異常系 RuntimeException
     */
    @Test
    fun test_failure_RuntimeException_getAuthor() {

        whenever(authorService.getAuthor(1)).thenThrow(RuntimeException::class.java)

        mockMvc.perform(get("/api/author/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError) // 500
    }

    /**
     * AuthorController.insertAuthor 正常系
     */
    @Test
    fun test_success_insertAuthor() {

        val insertAuthorRequest = InsertAuthorRequest(
            name = "著者1",
            birthday = "19750101",
        )

        whenever(authorService.insertAuthor(insertAuthorRequest)).then { }

        val requestBody: String = """
            {
              "name": "著者1",
              "birthday": "19750101"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
            .andExpect(status().isOk)
    }

    /**
     * AuthorController.insertAuthor 異常系 request.name不正
     */
    @Test
    fun test_failure_request_name_insertAuthor() {

        val requestBody: String = """
            {
              "name": "",
              "birthday": "19750101"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
            .andExpect(status().isBadRequest)
    }

    /**
     * AuthorController.insertAuthor 異常系 request.birthday不正
     */
    @Test
    fun test_failure_request_birthday_insertAuthor() {

        // birthday 7桁
        val requestBody1: String = """
            {
              "name": "著者1",
              "birthday": "1975010"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody1)
        )
            .andExpect(status().isBadRequest)

        // birthday 9桁
        val requestBody2: String = """
            {
              "name": "著者1",
              "birthday": "197501011"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody2)
        )
            .andExpect(status().isBadRequest)
    }

    /**
     * AuthorController.insertAuthor 異常系 RuntimeException発生
     */
    @Test
    fun test_failure_RuntimeException_insertAuthor() {

        val insertAuthorRequest = InsertAuthorRequest(
            name = "著者1",
            birthday = "19750101",
        )

        whenever(authorService.insertAuthor(insertAuthorRequest)).thenThrow(RuntimeException::class.java)

        val requestBody: String = """
            {
              "name": "著者1",
              "birthday": "19750101"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
            .andExpect(status().isInternalServerError)
    }

    /**
     * AuthorController.updateAuthor 正常系
     */
    @Test
    fun test_success_updateAuthor() {

        val updateAuthorRequest = UpdateAuthorRequest(
            id = 1,
            name = "著者1",
            birthday = "19750101"
        )

        whenever(authorService.updateAuthor(updateAuthorRequest)).then { }

        val requestBody: String = """
            {
              "id": 1,
              "name": "著者1",
              "birthday": "19750101"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
            .andExpect(status().isOk)
    }

    /**
     * AuthorController.updateAuthor 異常系　request.id不正
     */
    @Test
    fun test_failure_request_id_updateAuthor() {

        val requestBody: String = """
            {
              "id": 0,
              "name": "著者1",
              "birthday": "19750101"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
            .andExpect(status().isBadRequest)
    }

    /**
     * AuthorController.updateAuthor 異常系　request.name不正
     */
    @Test
    fun test_failure_request_name_updateAuthor() {

        val requestBody: String = """
            {
              "id": 1,
              "name": "",
              "birthday": "19750101"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
            .andExpect(status().isBadRequest)
    }

    /**
     * AuthorController.updateAuthor 異常系　request.birthday不正
     */
    @Test
    fun test_failure_request_birthday_updateAuthor() {

        val requestBody1: String = """
            {
              "id": 1,
              "name": "著者1",
              "birthday": "1975010"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody1)
        )
            .andExpect(status().isBadRequest)

        val requestBody2: String = """
            {
              "id": 1,
              "name": "著者1",
              "birthday": "197501011"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody2)
        )
            .andExpect(status().isBadRequest)

    }

    /**
     * AuthorController.updateAuthor 異常系　NotFoundException
     */
    @Test
    fun test_failure_NotFoundException_updateAuthor() {

        val updateAuthorRequest = UpdateAuthorRequest(
            id = 1,
            name = "著者1",
            birthday = "19750101"
        )

        whenever(authorService.updateAuthor(updateAuthorRequest)).thenThrow(NotFoundException::class.java)

        val requestBody: String = """
            {
              "id": 1,
              "name": "著者1",
              "birthday": "19750101"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
            .andExpect(status().isNotFound)
    }

    /**
     * AuthorController.updateAuthor 異常系　RuntimeException
     */
    @Test
    fun test_failure_RuntimeException_updateAuthor() {

        val updateAuthorRequest = UpdateAuthorRequest(
            id = 1,
            name = "著者1",
            birthday = "19750101"
        )

        whenever(authorService.updateAuthor(updateAuthorRequest)).thenThrow(RuntimeException::class.java)

        val requestBody: String = """
            {
              "id": 1,
              "name": "著者1",
              "birthday": "19750101"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
            .andExpect(status().isInternalServerError)
    }
}