package com.example.codingtest.common.config

import org.jooq.DSLContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.jooq.impl.DSL
import javax.sql.DataSource

/**
 * DataSourceConfig
 */
@Configuration
class DataSourceConfig {

    /**
     * jooq DBアクセス用 dslContextをBean登録
     */
    @Bean
    fun dslContext(dataSource: DataSource): DSLContext {
        return DSL.using(dataSource.connection)
    }
}