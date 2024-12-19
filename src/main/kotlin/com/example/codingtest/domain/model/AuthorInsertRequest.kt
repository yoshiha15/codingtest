package com.example.codingtest.domain.model

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


data class AuthorInsertRequest(
    @field:NotEmpty
    val name: String,
    @field:Size(min=8, max=8)
    val birthday: String,
)
