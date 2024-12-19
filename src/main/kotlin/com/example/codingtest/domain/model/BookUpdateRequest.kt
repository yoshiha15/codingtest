package com.example.codingtest.domain.model

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


data class BookUpdateRequest(
    @field:Min(1)
    val id: Int,
    @field:NotEmpty
    val title: String,
    @field:Min(0)
    val price: Int,
    @field:Size(min=1, max=1)
    val publish: String,
    @field:NotEmpty
    val authorIds: List<Int>
)
