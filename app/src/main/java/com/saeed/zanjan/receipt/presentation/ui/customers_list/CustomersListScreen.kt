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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.domain.models.Customer
import com.saeed.zanjan.receipt.presentation.components.CustomAcceptDialog
import com.saeed.zanjan.receipt.presentation.components.CustomSMSTextDialog
import com.saeed.zanjan.receipt.presentation.components.CustomersBottomBar
import com.saeed.zanjan.receipt.presentation.components.CustomersListTopBar
import com.saeed.zanjan.receipt.presentation.components.ListOfCustomers
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomersListScreen(
    viewModel:CustomersListViewModel,
    navController: NavController
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope= rememberCoroutineScope()
    val context= LocalContext.current

    val loading = viewModel.loading.value
    val customersList = viewModel.customersList.value

    val focusRequester = remember { FocusRequester() }

    var isSearchExpanded by remember { mutableStateOf(false) }
    var openFilterDialog by remember { mutableStateOf(false) }
    var openDeleteDialog by remember { mutableStateOf(-1) }
    var openSMSTextDialog by remember { mutableStateOf(false) }



    var filtered by remember { mutableStateOf(false) }
    var selectedCustomers = mutableListOf<Customer>()

    LaunchedEffect(Unit){
        viewModel.getListOfCustomers(snackbarHostState)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.restartState()
            selectedCustomers.clear()
        }
    }



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
                        navController.popBackStack()

                    },
                    searchExit = {
                        viewModel.restartState()
                        viewModel.getListOfCustomers(snackbarHostState)
                    }
                )
            },
            bottomBar = {
                CustomersBottomBar(cardClick = {
                    if(selectedCustomers.isEmpty()){
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("لطفا مخاطب را انتخاب کنید.")

                        }
                    }else{
                        openSMSTextDialog=true

                    }
                })
            }

        ) {

            if(openSMSTextDialog){

                CustomSMSTextDialog(onDismiss = {
                           openSMSTextDialog=false
                }, onAccept ={message->

                        val destinations= mutableListOf<String>()
                        selectedCustomers.forEach {cus->
                            cus.phoneNumber?.let { it1 -> destinations.add(it1) }
                        }
                        viewModel.sendSmsToCustomers(
                            message=message,
                            destinations =destinations ,
                            snackbarHostState=snackbarHostState
                        )


                    openSMSTextDialog=false

                } )
            }
            if(openDeleteDialog!=-1){
                CustomAcceptDialog(
                    onDismiss = {openDeleteDialog=-1 },
                    onAccept = {
                        viewModel.deleteCustomer(openDeleteDialog,snackbarHostState)
                        customersList.removeAt(customersList.indexOfFirst { it.id == openDeleteDialog })

                        openDeleteDialog=-1
                    },
                    title ="حذف مشتری" ,
                    description ="آیا حذف این مورد از لیست مشتریان مطمئنید؟" ,
                    acceptText = "حذف"
                )
            }



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
                        onDelete = {id->
                                openDeleteDialog=id
                        },
                        onSelect = {cus->
                               selectedCustomers.add(cus)

                        },
                        deSelect = {cus->
                            selectedCustomers.remove(cus)

                        }
                    )

                }


            }

        }
}
}