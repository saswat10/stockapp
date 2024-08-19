package com.example.template.presentation.company_info

import com.example.template.domain.model.CompanyInfo
import com.example.template.domain.model.Intraday

data class CompanyInfoState(
    val stockInfo: List<Intraday> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String?=null


)
