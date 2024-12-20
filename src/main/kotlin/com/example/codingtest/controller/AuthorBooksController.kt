package com.example.codingtest.controller

import com.example.codingtest.domain.model.response.GetAuthorBooksResponse
import com.example.codingtest.domain.service.AuthorBooksService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthorBooksController(
    private val authorBooksService: AuthorBooksService
) {

    /**
     * DBに格納されている著者・書籍の情報を一覧で取得する
     * ※課題範囲外だがDBから取得・更新に必要なidを取得するために実装
     */
    @GetMapping("/author-books")
    fun getAuthorBooks(): List<GetAuthorBooksResponse>  {
        return authorBooksService.getAuthorBooks().map {
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
    }
}