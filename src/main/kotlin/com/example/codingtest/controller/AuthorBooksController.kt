package com.example.codingtest.controller

import com.example.codingtest.domain.model.AuthorBookResponse
import com.example.codingtest.domain.service.AuthorBooksService
import com.example.codingtest.domain.tables.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthorBooksController {

    @Autowired
    lateinit var authorBooksService: AuthorBooksService

    /**
     * DBに格納されている著者・書籍の情報を一覧で取得する
     * ※課題範囲外だがDBから取得・更新に必要なidを取得するために実装
     */
    @GetMapping("/author-books")
    fun authorBooks(): List<AuthorBookResponse>  {
        return authorBooksService.getAuthorBooks().map {
            AuthorBookResponse(
                authorId = it.getValue(Author.AUTHOR.ID),
                name = it.getValue(Author.AUTHOR.NAME),
                birthday = it.getValue(Author.AUTHOR.BIRTHDAY),
                bookId = it.getValue(Book.BOOK.ID),
                title = it.getValue(Book.BOOK.TITLE),
                price = it.getValue(Book.BOOK.PRICE),
                publish = it.getValue(Book.BOOK.PUBLISH),
            )
        }
    }
}