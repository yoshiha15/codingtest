package com.example.codingtest.common.config

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.RollbackOn
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    @Bean
    fun dslContext(dataSource: DataSource): DSLContext {
        return DSL.using(dataSource.connection)
    }
}