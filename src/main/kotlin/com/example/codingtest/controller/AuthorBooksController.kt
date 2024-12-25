package com.example.codingtest.controller

import com.example.codingtest.domain.model.response.GetAuthorBooksResponse
import com.example.codingtest.domain.service.AuthorBooksService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * AuthorBooksController
 */
@RestController
@RequestMapping("/api")
class AuthorBooksController(
    private val authorBooksService: AuthorBooksService
) {

    /**
     * 著者・書籍 ID取得用API
     * DBに格納されている著者・書籍の情報を一覧で取得する
     *
     * @param
     *
     * @return GetAuthorBooksResponse
     *             authorId  (著者)著者ID
     *             name      (著者)著者名
     *             birthday  (著者)誕生日
     *             bookId    (書籍)書籍ID
     *             title     (著者)タイトル
     *             price     (著者)価格
     *             publish   (著者)出版状況
     *
     */
    @GetMapping("/author-books-ids")
    fun getAuthorBooksIds(): ResponseEntity<List<GetAuthorBooksResponse>>  {

        return try {
            val getAuthorBooksResponse = authorBooksService.getAuthorBooks().map {
                GetAuthorBooksResponse(
                    authorId = it.authorId,
                    name = it.name,
                    birthday = it.birthday,
                    bookId = it.bookId,
                    title = it.title,
                    price = it.price,
                    publish = it.publish,
                )
            }
            ResponseEntity.ok(getAuthorBooksResponse)
        } catch (e: Exception) {
            // システムエラー
            // http status 500
            ResponseEntity.internalServerError().build()
        }
    }
}
