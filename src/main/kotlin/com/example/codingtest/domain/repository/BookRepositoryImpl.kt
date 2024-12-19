package com.example.codingtest.domain.repository

import com.example.codingtest.domain.enum.Publish
import com.example.codingtest.domain.exception.CannotUpdatedException
import com.example.codingtest.domain.tables.Book
import com.example.codingtest.util.DateUtils
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Record1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class BookRepositoryImpl: BookRepository {

    @Autowired
    lateinit var dslContext: DSLContext

    override fun getBooks(bookIds: List<Int>): List<Record> {
        return dslContext.select(
            Book.BOOK.ID,
            Book.BOOK.TITLE,
            Book.BOOK.PRICE,
            Book.BOOK.PUBLISH
        )
            .from(Book.BOOK)
            .where(Book.BOOK.ID.`in`(bookIds))
            .toList()
    }

    override fun insertBook(book: com.example.codingtest.domain.model.Book): Int {

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
            .values(book.title, book.price, book.publish, nowDateTime, nowDateTime)
            .returningResult(Book.BOOK.ID)
            .fetchOne()

        // null（登録失敗）の場合はエラー
        insertedBookId ?: throw RuntimeException()

        return insertedBookId.getValue(Book.BOOK.ID)
    }

    override fun updateBook(book: com.example.codingtest.domain.model.Book) {
        // 現在日時取得
        val nowDateTime: String = DateUtils.nowDateTime()

        // 現在の出版状況をチェック
        val beforePublish: String? = dslContext.select(Book.BOOK.PUBLISH)
            .from(Book.BOOK)
            .where(Book.BOOK.ID.equal(book.bookId))
            .fetchOne()?.getValue(Book.BOOK.PUBLISH)

        // 既に出版状況が出版済みの場合は未出版に更新不可
        if(beforePublish == Publish.ISSUED.value && book.publish == Publish.UNISSUED.value) {
            throw CannotUpdatedException()
        }

        dslContext.update(Book.BOOK)
            .set(Book.BOOK.TITLE, book.title)
            .set(Book.BOOK.PRICE, book.price)
            .set(Book.BOOK.PUBLISH, book.publish)
            .set(Book.BOOK.UPDATED, nowDateTime)
            .where(Book.BOOK.ID.eq(book.bookId))
            .execute()
    }
}