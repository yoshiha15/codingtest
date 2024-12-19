package com.example.codingtest.domain.model

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
)
