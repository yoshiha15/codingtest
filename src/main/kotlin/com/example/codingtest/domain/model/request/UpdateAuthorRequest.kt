package com.example.codingtest.domain.model.request

import com.example.codingtest.common.util.DateUtils
import com.example.codingtest.common.validator.PastDate
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


data class UpdateAuthorRequest(
    @field:Min(1)
    val id: Int,
    @field:NotEmpty
    val name: String,
    @field:Size(min=8, max=8)
    @field:PastDate
    val birthday: String,
)