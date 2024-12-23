package com.example.codingtest.domain.model.request

import com.example.codingtest.common.validator.PastDate
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

/**
 * InsertAuthorRequest
 */
data class InsertAuthorRequest(
    @field:NotEmpty
    val name: String,
    @field:Size(min=8, max=8)
    @field:PastDate
    val birthday: String,
)