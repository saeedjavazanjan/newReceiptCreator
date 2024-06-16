package com.saeed.zanjan.receipt.presentation.ui.customers_list

import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.presentation.components.AddReceiptCard
import com.saeed.zanjan.receipt.presentation.components.HomeTopBar
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import com.saeed.zanjan.receipt.presentation.ui.home.ListOfReceipts
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomersListScreen(
    viewModel:CustomersListViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope= rememberCoroutineScope()
    val context= LocalContext.current

    val loading = viewModel.loading.value

    NewReceiptCreatorTheme(
        displayProgressBar = loading,
        themColor = Color.Transparent
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                HomeTopBar(
                    modifier = Modifier.padding(15.dp),
                    focusRequester = focusRequester,
                    isSearchExpanded = isSearchExpanded,
                    expandSearchBar = {
                        isSearchExpanded = it
                    },
                    search = {

                        viewModel.searchReceipt(
                            it,
                            snackbarHostState
                        )

                    },
                    filter = {
                        openFilterDialog = true

                    },
                    menu = {

                    }
                )
            },
            bottomBar = {

            }

        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = it.calculateTopPadding(),
                    )
            ) {
                if(filtered){
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                        ,
                        onClick = {
                            viewModel.getListOfReceipts(snackbarHostState)
                            filtered=false
                        },
                        border = BorderStroke(width = 2.dp, color = CustomColors.lightGray)
                    ) {
                        Text(text = "حذف فیلتر",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CustomColors.darkPurple
                        )
                    }
                }
                if (!loading && receiptsList.value.isEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Icon(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                painter = painterResource(R.drawable.folder_1),
                                tint = CustomColors.gray,
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "لیست خالی است",
                            style = MaterialTheme.typography.titleLarge,
                            color = CustomColors.gray
                        )
                    }



                } else {
                    ListOfReceipts(
                        modifier = Modifier
                            .weight(1f)
                            .padding(),
                        receiptCategory = viewModel.receiptCategory,
                        receipts = receiptsList.value,
                        navigateToScreen = { id ->
                            val route =
                                Screen.Receipt.route + "/${id}/${false}/${false}/${false}/${false}"
                            navigateToReceiptScreen(route)
                        }
                    )

                }


            }

        }
}
}