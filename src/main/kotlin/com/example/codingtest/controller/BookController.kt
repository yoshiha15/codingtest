package com.example.codingtest.controller

import com.example.codingtest.domain.exception.CannotUpdatedException
import com.example.codingtest.domain.model.*
import com.example.codingtest.domain.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController {

    @Autowired
    lateinit var bookService: BookService
    /**
     * 著者登録
     */
    @PostMapping("/book")
    fun insertAuthor(@RequestBody @Validated bookInsertRequest: BookInsertRequest,
                     bindingResult: BindingResult
    ): ResponseEntity<Unit>  {

        // バリデーションチェックエラー
        if (bindingResult.hasErrors()) {
            // http status 400
            return ResponseEntity.badRequest().build()
        }

        return try {
            // 書籍登録
            bookService.insertBook(bookInsertRequest)
            return ResponseEntity.ok().build()
        } catch (e: Exception) {
            // http status 500
            ResponseEntity.internalServerError().build()
        }
    }

    /**
     * 書籍更新
     */
    @PatchMapping("/book")
    fun updateBook(@RequestBody @Validated bookUpdateRequest: BookUpdateRequest, bindingResult: BindingResult): ResponseEntity<Unit> {
        // バリデーションチェックエラー
        if (bindingResult.hasErrors()) {
            // http status 400
            return ResponseEntity.badRequest().build()
        }

        return try {
            // 著者更新
            bookService.updateBook(bookUpdateRequest)
            return ResponseEntity.ok().build()
        } catch(e: CannotUpdatedException) {
            // 出版済みステータスのものを未出版に更新しようとした場合
            // http status 499
            ResponseEntity.status(499).build()
        } catch (e: Exception) {
            // http status 500
            ResponseEntity.internalServerError().build()
        }
    }
}
