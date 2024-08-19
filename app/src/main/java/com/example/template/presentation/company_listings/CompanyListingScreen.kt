package com.example.template.presentation.company_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.template.CompanyDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListingScreen(
    viewModel: CompanyListingViewModel = hiltViewModel(),
    lazyListState: LazyListState = rememberLazyListState(),
    navController: NavController
) {
    var swipeRefreshState = rememberPullToRefreshState()
    val state = viewModel.state
    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(swipeRefreshState.nestedScrollConnection)
    ) {
        Column {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = {
                    viewModel.onEvent(
                        CompanyListingEvent.OnSearchQueryChange(it)
                    )
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Search...")
                },
                maxLines = 1,
                singleLine = true
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = lazyListState,
            ) {
                items(state.companies.size) { i ->
                    val company = state.companies[i]
                    CompanyListingItem(
                        company = company,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(CompanyDetail(company.symbol))
                            }
                            .padding(16.dp)
                    )
                    HorizontalDivider(
                        thickness = 0.2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }


        if (swipeRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                viewModel.onEvent(CompanyListingEvent.Refresh)
            }
        }
        LaunchedEffect(state.isRefreshing) {
            if (state.isRefreshing) {
                swipeRefreshState.startRefresh()
            } else {
                swipeRefreshState.endRefresh()
            }
        }
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = swipeRefreshState,

            )
    }
}
