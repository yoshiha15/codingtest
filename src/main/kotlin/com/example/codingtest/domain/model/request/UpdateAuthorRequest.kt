package com.example.codingtest.domain.model.request

import com.example.codingtest.common.validator.PastDate
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

/**
 * UpdateAuthorRequest
 */
data class UpdateAuthorRequest(
    @field:Min(1)
    val authorId: Int,
    @field:NotEmpty
    val name: String,
    @field:Size(min=8, max=8)
    @field:PastDate
    val birthday: String,
)
