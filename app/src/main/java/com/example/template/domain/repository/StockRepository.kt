package com.example.template.domain.repository

import com.example.template.domain.model.CompanyInfo
import com.example.template.domain.model.CompanyListing
import com.example.template.domain.model.Intraday
import com.example.template.util.Resource
import kotlinx.coroutines.flow.Flow


// since we have a local cache here we will
// return a flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote : Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<Intraday>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>

}