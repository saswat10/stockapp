package com.example.template.domain.model

// this file just contains the type of data
// nothing related to any third-party libraries

// these domain level objects are used
// in our ui to show the data to the user.

data class CompanyListing(
    val name: String,
    val symbol: String,
    val exchange: String,
)