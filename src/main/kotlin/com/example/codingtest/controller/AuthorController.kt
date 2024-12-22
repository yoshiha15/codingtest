package com.example.codingtest.controller

import com.example.codingtest.common.exception.NotFoundException
import com.example.codingtest.domain.model.response.GetAuthorResponse
import com.example.codingtest.domain.model.request.InsertAuthorRequest
import com.example.codingtest.domain.model.request.UpdateAuthorRequest
import com.example.codingtest.domain.service.AuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AuthorController(
    private val authorService: AuthorService
) {

    @GetMapping("/author/{id}")
    fun getAuthor(@PathVariable("id") id: Int): ResponseEntity<GetAuthorResponse>{

        // パスパラメータに指定した著者IDが1以下であればエラー
        if (id < 1) {
            // http status 400
            return ResponseEntity.badRequest().build()
        }

        return try {
            ResponseEntity.ok(authorService.getAuthor(id))
        } catch (e: NotFoundException) {
            // 対象が存在しない場合
            // http status 404
            ResponseEntity.notFound().build()
        } catch (e: Exception) {
            // システムエラー
            // http status 500
            ResponseEntity.internalServerError().build()
        }
    }

    /**
     * 著者登録
     */
    @PostMapping("/author")
    fun insertAuthor(@RequestBody @Validated insertAuthorRequest: InsertAuthorRequest, bindingResult: BindingResult): ResponseEntity<Unit>  {

        // バリデーションチェックエラー
        if (bindingResult.hasErrors()) {
            // http status 400
            return ResponseEntity.badRequest().build()
        }

        return try {
            // 著者登録
            authorService.insertAuthor(insertAuthorRequest)
            return ResponseEntity.ok().build()
        } catch (e: Exception) {
            // システムエラー
            // http status 500
            ResponseEntity.internalServerError().build()
        }
    }

    /**
     * 著者更新
     */
    @PatchMapping("/author")
    fun updateAuthor(@RequestBody @Validated updateAuthorRequest: UpdateAuthorRequest, bindingResult: BindingResult): ResponseEntity<Unit> {
        // バリデーションチェックエラー
        if (bindingResult.hasErrors()) {
            // http status 400
            return ResponseEntity.badRequest().build()
        }

        return try {
            // 著者更新
            authorService.updateAuthor(updateAuthorRequest)
            return ResponseEntity.ok().build()
        } catch (e: NotFoundException) {
            // 対象が存在しない場合
            // http status 404
            ResponseEntity.notFound().build()
        } catch (e: Exception) {
            // システムエラー
            // http status 500
            ResponseEntity.internalServerError().build()
        }
    }
}
