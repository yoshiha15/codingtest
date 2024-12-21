package com.example.codingtest.common.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val FORMAT_DATETIME = "yyyyMMddHHmmss"

const val FORMAT_DATE = "yyyyMMdd"


class DateUtils {

    companion object {
        fun nowDateTime(): String {
            // 現在時刻の取得
            val now = LocalDateTime.now()
            // フォーマットの指定
            val dtf = DateTimeFormatter.ofPattern(FORMAT_DATETIME)
            return now.format(dtf)
        }

        fun nowDate(): String {
            // 現在時刻の取得
            val now = LocalDate.now()
            // フォーマットの指定
            val dtf = DateTimeFormatter.ofPattern(FORMAT_DATE)
            return now.format(dtf)
        }

    }
}