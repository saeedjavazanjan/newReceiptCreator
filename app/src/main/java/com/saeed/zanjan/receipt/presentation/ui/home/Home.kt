package com.saeed.zanjan.receipt.presentation.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.presentation.components.AddReceiptCard
import com.saeed.zanjan.receipt.presentation.components.HomeTopBar
import com.saeed.zanjan.receipt.presentation.components.ReceiptListCard
import com.saeed.zanjan.receipt.presentation.components.StatusDialog
import com.saeed.zanjan.receipt.presentation.components.SubscribeDialog
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(
    viewModel: HomeViewModel,
    navigateToReceiptScreen: (String) -> Unit,
    navigateToCreateReceiptScreen: () -> Unit,
    navigateToProfileSetting:()->Unit,
    navigateToAboutUs:()->Unit,
    navigateToCustomersList:()->Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope= rememberCoroutineScope()
    val context= LocalContext.current
    val loading = viewModel.loading.value
    val receiptsList = viewModel.receiptList

    var isSearchExpanded by remember { mutableStateOf(false) }
    var openFilterDialog by remember { mutableStateOf(false) }
    var openSubscribeDialog by remember { mutableStateOf(false) }
    var filtered by remember { mutableStateOf(false) }

    val drawerState =rememberDrawerState(initialValue = DrawerValue.Closed )
    val menuItems= listOf(
        NavigationItem(
            title = "تنظیمات پروفایل",
            icon = painterResource(id = R.drawable.settings)
        ),
        NavigationItem(
            title = "درباره ما",
            icon = painterResource(id = R.drawable.about_us)
        ),
        NavigationItem(
            title = "ثبت نظر",
            icon = painterResource(id = R.drawable.comment)
        ),
        NavigationItem(
            title = "لیست مشتریان",
            icon = painterResource(id = R.drawable.group_1)
        ),
        NavigationItem(
            title = "دریافت خروجی Excel",
            icon = painterResource(id = R.drawable.excel),
            premiumIcon = painterResource(id = R.drawable.star)
        ),
        NavigationItem(
            title = "پشتیبان گیری",
            icon = painterResource(id = R.drawable.upload),
            premiumIcon = painterResource(id = R.drawable.star)
        ),

        NavigationItem(
            title = "خرید اشتراک",
            icon = painterResource(id = R.drawable.credit_card)
        ),
        NavigationItem(
            title = "پنل اختصاصی (به زودی)",
            icon = painterResource(id = R.drawable.personal_panel)
        ),

    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(isSearchExpanded) {
        if (isSearchExpanded) {
            focusRequester.requestFocus()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getListOfReceipts(snackbarHostState)
       // viewModel.downloadDb(snackbarHostState)
    }
    LaunchedEffect(key1 = viewModel.databaseSaved.value){
       // viewModel.getListOfReceipts(snackbarHostState)
    }
    NewReceiptCreatorTheme(
        displayProgressBar = loading,
        themColor = Color.Transparent
    ) {

        ModalNavigationDrawer(
            drawerContent = {
                            ModalDrawerSheet(
                                modifier = Modifier.padding(end = 60.dp),
                            ) {
                                Spacer(modifier = Modifier.size(60.dp))
                                menuItems.forEachIndexed{index,item->
                                    NavigationDrawerItem(
                                        colors=NavigationDrawerItemDefaults.colors(
                                            selectedContainerColor = CustomColors.transparentBlue
                                        ),
                                        label = {
                                                Text(text = item.title)
                                                },
                                        selected = index==selectedItemIndex ,
                                        onClick = {
                                            selectedItemIndex=index
                                            coroutineScope.launch {
                                                drawerState.close()
                                            }
                                            when (index){
                                                0->{
                                                    navigateToProfileSetting()
                                                }
                                                1->{
                                                    navigateToAboutUs()
                                                }
                                                2->{
                                                  viewModel.commentOnApp(context)
                                                }
                                                3->{
                                                    navigateToCustomersList()
                                                }
                                                4->{
                                                    viewModel.exportExcel(snackbarHostState)
                                                }
                                                5->{
                                                    viewModel.uploadBackUpOfDatabase(snackbarHostState)
                                                }
                                                6->{
                                                   openSubscribeDialog=true
                                                }
                                                7->{
                                                    coroutineScope.launch {
                                                        snackbarHostState.showSnackbar("این امکان هنوز پیاده سازی نشده است.")

                                                    }
                                                }
                                            }

                                        },
                                        icon={
                                             Icon(painter =item.icon , contentDescription =item.title )
                                        },
                                        badge={
                                            item.premiumIcon?.let {
                                                Icon(painter = it,
                                                tint=CustomColors.gold,
                                                    contentDescription =null
                                                ) }
                                        },

                                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                    )
                                }
                            }

                            },
            drawerState=drawerState

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
                            coroutineScope.launch {
                                drawerState.open()

                            }
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

                if(openSubscribeDialog){
                    SubscribeDialog()
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


