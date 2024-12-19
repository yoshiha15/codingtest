package com.example.codingtest.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val FORMAT_DATETIME = "yyyyMMddHHmmss"

class DateUtils {

    companion object {
        fun nowDateTime(): String {
            // 現在時刻の取得
            val now = LocalDateTime.now()
            // フォーマットの指定
            val dtf = DateTimeFormatter.ofPattern(FORMAT_DATETIME)
            return now.format(dtf)
        }
    }
}