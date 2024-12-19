package com.example.codingtest

import com.example.codingtest.config.TestContainersConfiguration
import org.springframework.boot.fromApplication

class TestCodingTestApplication {
    fun main(args: Array<String>) {
        fromApplication<CodingTestApplication>().with(TestContainersConfiguration::class.java)
    }
}