package com.example.codingtest.domain.repository

import com.example.codingtest.domain.tables.Author
import com.example.codingtest.util.DateUtils
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class AuthorRepositoryImpl: AuthorRepository {

    @Autowired
    lateinit var dslContext: DSLContext

    override fun getAuthor(authorId: Int): Record {
        return dslContext.select(
            Author.AUTHOR.ID,
            Author.AUTHOR.NAME,
            Author.AUTHOR.BIRTHDAY
        )
            .from(Author.AUTHOR)
            .where(Author.AUTHOR.ID.eq(authorId))
            .fetchSingle()
    }

    override fun insertAuthor(name: String, birthday: String){

        // 現在日時取得
        val nowDateTime: String = DateUtils.nowDateTime()

        dslContext
            .insertInto(Author.AUTHOR,
                Author.AUTHOR.NAME,
                Author.AUTHOR.BIRTHDAY,
                Author.AUTHOR.UPDATED,
                Author.AUTHOR.CREATED
            )
            .values(name, birthday, nowDateTime, nowDateTime)
            .execute()
    }

    override fun updateAuthor(id: Int, name: String, birthday: String) {

        // 現在日時取得
        val nowDateTime: String = DateUtils.nowDateTime()

        dslContext.update(Author.AUTHOR)
            .set(Author.AUTHOR.NAME, name)
            .set(Author.AUTHOR.BIRTHDAY,birthday)
            .set(Author.AUTHOR.UPDATED, nowDateTime)
            .where(Author.AUTHOR.ID.eq(id))
            .execute()
    }
}