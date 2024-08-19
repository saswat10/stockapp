package com.example.template.data.repository

import com.example.template.data.csv.CSVParser
import com.example.template.data.local.StockDatabase
import com.example.template.data.mapper.toCompanyInfo
import com.example.template.data.mapper.toCompanyListing
import com.example.template.data.mapper.toCompanyListingEntity
import com.example.template.data.remote.StockApi
import com.example.template.domain.model.CompanyInfo
import com.example.template.domain.model.CompanyListing
import com.example.template.domain.model.Intraday
import com.example.template.domain.repository.StockRepository
import com.example.template.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


// we need to ensure that we should have a single stock repository in
// our project
@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingsParser: CSVParser<CompanyListing>,
    private val intradayParser: CSVParser<Intraday>
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {


        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldLoadFromCache) {
                emit(Resource.Loading(true))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not load data"))
                null
            }

            remoteListings?.let { companyListings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    companyListings.map { it.toCompanyListingEntity() }
                )
                emit(
                    Resource.Success(data = dao
                        .searchCompanyListing("")
                        .map { it.toCompanyListing() })
                )
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<Intraday>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val results = intradayParser.parse(response.byteStream())
            Resource.Success(results)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Could not load intraday data"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Could not load intraday data"
            )
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try{
            val response = api.getCompanyInfo(symbol)
            val results = response.toCompanyInfo()
            Resource.Success(results)
        }catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Could not load company data"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Could not load company data"
            )
        }
    }
}