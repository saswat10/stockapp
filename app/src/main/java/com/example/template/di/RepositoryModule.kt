package com.example.template.di

import com.example.template.data.csv.CSVParser
import com.example.template.data.csv.CompanyListingsParser
import com.example.template.data.csv.IntradayCSVParser
import com.example.template.data.repository.StockRepositoryImpl
import com.example.template.domain.model.CompanyListing
import com.example.template.domain.model.Intraday
import com.example.template.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayParser(
        intradayInfoParser: IntradayCSVParser
    ): CSVParser<Intraday>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}