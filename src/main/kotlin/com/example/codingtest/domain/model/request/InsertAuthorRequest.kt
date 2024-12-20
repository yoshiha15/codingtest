package com.example.codingtest.domain.model.request

import com.example.codingtest.util.DateUtils
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


data class InsertAuthorRequest(
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