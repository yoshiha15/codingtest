package com.example.codingtest.domain.model

import com.example.codingtest.util.DateUtils
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


data class AuthorUpdateRequest(
    @field:Min(1)
    val id: Int,
    @field:NotEmpty
    val name: String,
    @field:Size(min=8, max=8)
    val birthday: String,
) {
    // 誕生日が現在日付移行の場合エラー
    @AssertTrue
    fun isBefore(): Boolean {
        return java.lang.Long.valueOf(birthday) < java.lang.Long.valueOf(DateUtils.nowDate())
    }
}
