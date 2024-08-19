package com.example.template.presentation

sealed class Screen(val route: String) {
    object CompanyListingScreen: Screen("coin_list_screen")
    object CompanyDetail: Screen("coin_detail_screen")
}