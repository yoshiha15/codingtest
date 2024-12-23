package com.example.codingtest.controller

import com.example.codingtest.common.exception.NotFoundException
import com.example.codingtest.domain.model.response.GetAuthorResponse
import com.example.codingtest.domain.model.request.InsertAuthorRequest
import com.example.codingtest.domain.model.request.UpdateAuthorRequest
import com.example.codingtest.domain.service.AuthorService
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * AuthorController
 */
@RestController
@RequestMapping("/api")
class AuthorController(
    private val authorService: AuthorService
) {

    /**
     * 著者と著者に紐つく書籍のリストを取得する
     *
     * @param id 著者ID 必須 1以上であること
     *
     * @return GetAuthorResponse
     *             authorId  著者ID
     *             name      著者名
     *             birthday  誕生日
     *             books     書籍
     */
    @GetMapping("/author/{id}")
    fun getAuthor(@PathVariable("id") id: Int): ResponseEntity<GetAuthorResponse>{

        // パスパラメータに指定した著者IDが0以下であればエラー
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
     * 著者を登録する
     *
     * @param insertAuthorRequest
     *            name      著者名  必須  空文字列ではないこと
     *            birthday  誕生日  必須  8文字の日付yyyyMMdd)形式であること 現在より過去日であること
     *
     * @return
     *
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
     * 著者を更新する
     *
     * @param updateAuthorRequest
     *            authorId  著者ID  必須  1以上であること
     *            name      著者名  必須  空文字列ではないこと
     *            birthday  誕生日  必須  8文字の日付yyyyMMdd)形式であること 現在より過去日であること
     *
     * @return
     *
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
