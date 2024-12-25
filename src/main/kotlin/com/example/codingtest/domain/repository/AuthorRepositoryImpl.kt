package com.example.codingtest.domain.repository

import com.example.codingtest.common.exception.NotFoundException
import com.example.codingtest.domain.model.dto.AuthorDto
import com.example.codingtest.common.util.DateUtils
import com.example.codingtest.domain.jooq.tables.Author
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository

/**
 * AuthorRepositoryImpl
 */
@Repository
class AuthorRepositoryImpl(
    private val dslContext: DSLContext
): AuthorRepository {

    override fun getAuthor(authorId: Int): AuthorDto {
        val result: Record = dslContext.select(
            Author.AUTHOR.ID,
            Author.AUTHOR.NAME,
            Author.AUTHOR.BIRTHDAY
        )
            .from(Author.AUTHOR)
            .where(Author.AUTHOR.ID.eq(authorId))
            .orderBy(Author.AUTHOR.ID)
            .fetchOptional()
            .orElseThrow {
                throw NotFoundException()
            }

        return result.map {
            AuthorDto(
                authorId = it.getValue(Author.AUTHOR.ID),
                name = it.getValue(Author.AUTHOR.NAME),
                birthday = it.getValue(Author.AUTHOR.BIRTHDAY)
            )
        }
    }

    override fun insertAuthor(name: String, birthday: String){

        dslContext.transaction { _->
            // 現在日時取得
            val nowDateTime: String = DateUtils.nowDateTime()

            dslContext
                .insertInto(
                    Author.AUTHOR,
                    Author.AUTHOR.NAME,
                    Author.AUTHOR.BIRTHDAY,
                    Author.AUTHOR.UPDATED,
                    Author.AUTHOR.CREATED
                )
                .values(name, birthday, nowDateTime, nowDateTime)
                .execute()
        }
    }

    override fun updateAuthor(id: Int, name: String, birthday: String) {

        dslContext.transaction { _->
            // 現在日時取得
            val nowDateTime: String = DateUtils.nowDateTime()

            val result: Int = dslContext.update(Author.AUTHOR)
                .set(Author.AUTHOR.NAME, name)
                .set(Author.AUTHOR.BIRTHDAY,birthday)
                .set(Author.AUTHOR.UPDATED, nowDateTime)
                .where(Author.AUTHOR.ID.eq(id))
                .execute()

            // 更新対照が0件の場合はNotFoundException
            if (result == 0) {
                throw NotFoundException()
            }
        }
    }
}
