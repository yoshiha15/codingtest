package com.example.codingtest.domain.model.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


data class InsertBookRequest(
    @field:NotEmpty
    val title: String,
    @field:Min(0)
    val price: Int,
    @field:Size(min=1, max=1)
    val publish: String,
    @field:NotEmpty
    val authorIds: List<Int>
)

