package com.example.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.template.presentation.Screen
import com.example.template.presentation.company_info.CompanyInfoScreen
import com.example.template.presentation.company_listings.CompanyListingScreen
import com.example.template.ui.theme.TemplateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TemplateTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) {innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)){
                        val navController = rememberNavController()
                        NavHost(navController =navController,
                            startDestination = CompanyListingsScreen){
                            composable<CompanyListingsScreen> {
                                CompanyListingScreen(navController = navController)
                            }
                            composable<CompanyDetail>{
                                val args = it.toRoute<CompanyDetail>()
                                CompanyInfoScreen(symbol = args.symbol)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Serializable
object CompanyListingsScreen

@Serializable
data class CompanyDetail(
    val symbol: String
)