package com.example.codingtest.domain.repository

import com.example.codingtest.domain.enum.Publish
import com.example.codingtest.domain.exception.InvalidPublishUpdatedException
import com.example.codingtest.domain.jooq.tables.Book
import com.example.codingtest.domain.model.dto.BookDto
import com.example.codingtest.util.DateUtils
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Record1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class BookRepositoryImpl(
    private val dslContext: DSLContext
): BookRepository {

    override fun getBooks(bookIds: List<Int>): List<BookDto> {
        val result: List<Record> = dslContext.select(
            Book.BOOK.ID,
            Book.BOOK.TITLE,
            Book.BOOK.PRICE,
            Book.BOOK.PUBLISH
        )
            .from(Book.BOOK)
            .where(Book.BOOK.ID.`in`(bookIds))
            .toList()

        return result.map {
            BookDto(
                bookId = it.getValue(Book.BOOK.ID),
                title = it.getValue(Book.BOOK.TITLE),
                price = it.getValue(Book.BOOK.PRICE),
                publish = it.getValue(Book.BOOK.PUBLISH),
            )
        }
    }

    override fun insertBook(bookDto: BookDto): Int {

        // 現在日時取得
        val nowDateTime: String = DateUtils.nowDateTime()

        val insertedBookId: Record1<Int>? = dslContext.insertInto(
            Book.BOOK,
            Book.BOOK.TITLE,
            Book.BOOK.PRICE,
            Book.BOOK.PUBLISH,
            Book.BOOK.CREATED,
            Book.BOOK.UPDATED
        )
            .values(bookDto.title, bookDto.price, bookDto.publish, nowDateTime, nowDateTime)
            .returningResult(Book.BOOK.ID)
            .fetchOne()

        // null（登録失敗）の場合はエラー
        insertedBookId ?: throw RuntimeException()

        return insertedBookId.getValue(Book.BOOK.ID)
    }

    override fun updateBook(bookDto: BookDto) {
        // 現在日時取得
        val nowDateTime: String = DateUtils.nowDateTime()

        // 現在の出版状況をチェック
        val beforePublish: String? = dslContext.select(Book.BOOK.PUBLISH)
            .from(Book.BOOK)
            .where(Book.BOOK.ID.equal(bookDto.bookId))
            .fetchOne()?.getValue(Book.BOOK.PUBLISH)

        // 既に出版状況が出版済みの場合は未出版に更新不可
        if(beforePublish == Publish.ISSUED.value && bookDto.publish == Publish.UNISSUED.value) {
            throw InvalidPublishUpdatedException()
        }

        dslContext.update(Book.BOOK)
            .set(Book.BOOK.TITLE, bookDto.title)
            .set(Book.BOOK.PRICE, bookDto.price)
            .set(Book.BOOK.PUBLISH, bookDto.publish)
            .set(Book.BOOK.UPDATED, nowDateTime)
            .where(Book.BOOK.ID.eq(bookDto.bookId))
            .execute()
    }
}