package com.example.codingtest.controller

import com.example.codingtest.common.exception.InvalidPublishUpdatedException
import com.example.codingtest.common.exception.NotFoundException
import com.example.codingtest.domain.model.request.InsertBookRequest
import com.example.codingtest.domain.model.request.UpdateBookRequest
import com.example.codingtest.domain.service.BookService
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    private val bookService: BookService
) {

    /**
     * 書籍登録
     */
    @PostMapping("/book")
    fun insertBook(@RequestBody @Validated insertBookRequest: InsertBookRequest,
                     bindingResult: BindingResult
    ): ResponseEntity<Unit>  {

        // バリデーションチェックエラー
        if (bindingResult.hasErrors()) {
            // http status 400
            return ResponseEntity.badRequest().build()
        }

        return try {
            // 書籍登録
            bookService.insertBook(insertBookRequest)
            return ResponseEntity.ok().build()
        } catch(e: NotFoundException) {
            // 対照が存在しない場合
            // http status 404
            ResponseEntity.notFound().build()
        } catch (e: Exception) {
            // システムエラー
            // http status 500
            ResponseEntity.internalServerError().build()
        }
    }

    /**
     * 書籍更新
     */
    @PatchMapping("/book")
    fun updateBook(@RequestBody @Validated updateBookRequest: UpdateBookRequest, bindingResult: BindingResult): ResponseEntity<Unit> {
        // バリデーションチェックエラー
        if (bindingResult.hasErrors()) {
            // http status 400
            return ResponseEntity.badRequest().build()
        }

        return try {
            // 著者更新
            bookService.updateBook(updateBookRequest)
            return ResponseEntity.ok().build()
        } catch(e: NotFoundException) {
            // 対照が存在しない場合
            // http status 404
            ResponseEntity.notFound().build()
        } catch(e: InvalidPublishUpdatedException) {
            // 出版済みステータスの書籍.出版状況を未出版に更新しようとした場合
            // http status 400
            ResponseEntity.badRequest().build()
        } catch (e: Exception) {
            // システムエラー
            // http status 500
            ResponseEntity.internalServerError().build()
        }
    }
}
