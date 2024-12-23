package com.example.codingtest.common.validator

import com.example.codingtest.common.util.DateUtils
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

/**
 * PastDateValidator
 * 値が現在日付より過去日であることをチェックするバリデーション処理
 * 値のフォーマット: yyyyMMdd
 */
class PastDateValidator: ConstraintValidator<PastDate, String> {

    override fun initialize(constraintAnnotation: PastDate) {}

    /**
     * バリデーション処理
     */
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {

        if(value == null) return true
        // 現在日付より過去日であること
        return java.lang.Long.valueOf(value) < java.lang.Long.valueOf(DateUtils.nowDate())
    }

}