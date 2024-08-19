package com.example.template.data.mapper

import com.example.template.data.local.CompanyListingEntity
import com.example.template.data.remote.dto.CompanyInfoDto
import com.example.template.domain.model.CompanyInfo
import com.example.template.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        industry = industry ?: "",
        description = description ?: "",
        symbol = symbol ?: "",
        name = name ?: "",
        country = country ?: ""
    )
}