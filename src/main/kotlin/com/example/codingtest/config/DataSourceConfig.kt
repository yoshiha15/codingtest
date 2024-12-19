package com.example.codingtest.config

import org.jooq.DSLContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Value

@Configuration
open class DataSourceConfig {

    @Value("\${spring.datasource.url}")
    val url = ""

    @Value("\${spring.datasource.username}")
    val username = ""

    @Value("\${spring.datasource.password}")
    val password = ""

    @Bean
    fun dslContext(): DSLContext {
        return DSL.using(url, username, password)
    }
}