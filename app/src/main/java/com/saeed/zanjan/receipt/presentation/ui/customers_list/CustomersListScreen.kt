package com.saeed.zanjan.receipt.presentation.ui.customers_list

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.domain.models.Customer
import com.saeed.zanjan.receipt.presentation.components.CustomersListTopBar
import com.saeed.zanjan.receipt.presentation.components.ListOfCustomers
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomersListScreen(
    viewModel:CustomersListViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope= rememberCoroutineScope()
    val context= LocalContext.current

    val loading = viewModel.loading.value
    val customersList = mutableListOf(
        Customer(1, "Alice", "1234567890", 1500000),
        Customer(2, "Bob", "0987654321", 1000000),
        Customer(3, "Charlie", "1122334455", 500000)
    )// viewModel.customersList

    val focusRequester = remember { FocusRequester() }

    var isSearchExpanded by remember { mutableStateOf(false) }
    var openFilterDialog by remember { mutableStateOf(false) }
    var filtered by remember { mutableStateOf(false) }



    NewReceiptCreatorTheme(
        displayProgressBar = loading,
        themColor = Color.Transparent
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CustomersListTopBar(
                    modifier = Modifier.padding(15.dp),
                    focusRequester = focusRequester,
                    isSearchExpanded = isSearchExpanded,
                    expandSearchBar = {
                        isSearchExpanded = it
                    },
                    search = {

                        viewModel.searchCustomer(
                            it,
                            snackbarHostState
                        )

                    },
                    filter = {
                        openFilterDialog = true

                    },
                    back = {

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
                            viewModel.getListOfCustomers(snackbarHostState)
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
                if (!loading && customersList.isEmpty()) {
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
                    ListOfCustomers(
                        modifier = Modifier
                            .weight(1f)
                            .padding(),
                        receiptCategory = viewModel.receiptCategory,
                        customers = customersList,

                    )

                }


            }

        }
}
}