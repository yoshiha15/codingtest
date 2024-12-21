package com.example.codingtest.common.validator

import jakarta.validation.Constraint
import jakarta.validation.Payload
import jakarta.validation.ReportAsSingleViolation
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@ReportAsSingleViolation
@Constraint(validatedBy = [PastDateValidator::class])
annotation class PastDate(
    val message: String = "",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = []
)

