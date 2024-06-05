package com.saeed.zanjan.receipt.presentation.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.presentation.components.AddReceiptCard
import com.saeed.zanjan.receipt.presentation.components.HomeTopBar
import com.saeed.zanjan.receipt.presentation.components.ReceiptListCard
import com.saeed.zanjan.receipt.presentation.components.StatusDialog
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(
    viewModel: HomeViewModel,
    navigateToReceiptScreen: (String) -> Unit,
    navigateToCreateReceiptScreen: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }

    val loading = viewModel.loading.value
    val receiptsList = viewModel.receiptList

    var isSearchExpanded by remember { mutableStateOf(false) }
    var openFilterDialog by remember { mutableStateOf(false) }
    var filtered by remember { mutableStateOf(false) }


    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(isSearchExpanded) {
        if (isSearchExpanded) {
            focusRequester.requestFocus()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getListOfReceipts(snackbarHostState)
    }
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

                    }
                )
            },
            bottomBar = {
                AddReceiptCard(
                    modifier = Modifier
                        .background(Color.Transparent),
                    addButtonClicked = {
                        navigateToCreateReceiptScreen()
                    }
                )
            }
        ) {

            if (openFilterDialog) {
                StatusDialog(onDismiss = {
                                         openFilterDialog=false

                },

                    onStatusSelected = {stat->
                        viewModel.filterReceipt(stat ,snackbarHostState)
                        openFilterDialog=false
                        filtered=true
                    }
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
                if (receiptsList.value.isEmpty() && !loading) {
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

@Composable
fun ListOfReceipts(
    modifier: Modifier,
    receiptCategory: Int,
    receipts: List<GeneralReceipt>,
    navigateToScreen: (Int) -> Unit

) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier
    ) {
        itemsIndexed(
            items = receipts
        ) { index, rec ->

            ReceiptListCard(
                receiptCategory = receiptCategory,
                receipt = rec,
                onReceiptClickListener = {
                    navigateToScreen(it)

                }
            )

        }
        item {
            Spacer(modifier = Modifier.height(100.dp)) // Adjust the height based on BottomAppBar height
        }
    }

}


