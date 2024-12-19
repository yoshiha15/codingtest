package com.example.codingtest.controller

import com.example.codingtest.domain.model.AuthorGetResponse
import com.example.codingtest.domain.model.AuthorInsertRequest
import com.example.codingtest.domain.model.AuthorUpdateRequest
import com.example.codingtest.domain.service.AuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorController {

    @Autowired
    lateinit var authorService: AuthorService

    @GetMapping("/author/{id}")
    fun getAuthor(@PathVariable("id") id: Int): ResponseEntity<AuthorGetResponse>{

        // パスパラメータに指定した著者IDが1以下であればエラー
        if (id < 1) {
            // http status 400
            return ResponseEntity.badRequest().build()
        }

        return try {
            ResponseEntity.ok(authorService.getAuthor(id))
        } catch (e: Exception) {
            // 500
            ResponseEntity.internalServerError().build()
        }
    }

    /**
     * 著者登録
     */
    @PostMapping("/author")
    fun insertAuthor(@RequestBody @Validated authorInsertRequest: AuthorInsertRequest, bindingResult: BindingResult): ResponseEntity<Unit>  {

        // バリデーションチェックエラー
        if (bindingResult.hasErrors()) {
            // http status 400
            return ResponseEntity.badRequest().build()
        }

        return try {
            // 著者登録
            authorService.insertAuthor(authorInsertRequest)
            return ResponseEntity.ok().build()
        } catch (e: Exception) {
            // http status 500
            ResponseEntity.internalServerError().build()
        }
    }

    /**
     * 著者更新
     */
    @PatchMapping("/author")
    fun updateAuthor(@RequestBody @Validated authorUpdateRequest: AuthorUpdateRequest, bindingResult: BindingResult): ResponseEntity<Unit> {
        // バリデーションチェックエラー
        if (bindingResult.hasErrors()) {
            // http status 400
            return ResponseEntity.badRequest().build()
        }

        return try {
            // 著者更新
            authorService.updateAuthor(authorUpdateRequest)
            return ResponseEntity.ok().build()
        } catch (e: Exception) {
            // http status 500
            ResponseEntity.internalServerError().build()
        }
    }
}
