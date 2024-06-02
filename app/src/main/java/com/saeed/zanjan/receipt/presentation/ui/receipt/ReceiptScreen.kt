package com.saeed.zanjan.receipt.presentation.ui.receipt

import HorizontalDashedLine
import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.presentation.components.BottomBar
import com.saeed.zanjan.receipt.presentation.components.CustomAcceptDialog
import com.saeed.zanjan.receipt.presentation.components.ReceiptCard
import com.saeed.zanjan.receipt.presentation.components.SendSmsDialog
import com.saeed.zanjan.receipt.presentation.components.TopBar
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReceiptScreen(
    viewModel: ReceiptViewModel,
    receiptId: Int?,
    navController: NavController,
    newSaved:Boolean=false,
    newUpdate:Boolean=false,
    statusChanged:Boolean=false,
    paymentChanged:Boolean=false,
) {
    val receiptCategory = viewModel.receiptCategory

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var currentReceipt = viewModel.currentReceipt

    val loading = viewModel.loading.value
    val deleteState = viewModel.deleteState.value


    val openDeleteDialog = remember { mutableStateOf(false) }
    val openSendSmsDialog = remember { mutableStateOf(false) }
    val openStatusSendSMSDialog = remember { mutableStateOf(false) }
    val openPaymentSendSMSDialog = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = deleteState){
        if(deleteState)
            navController.popBackStack()
    }


    LaunchedEffect(newSaved){
        if(newSaved){
            snackbarHostState.showSnackbar("با موفقیت ذخیره شد",duration = SnackbarDuration.Short)
            openSendSmsDialog.value=true
        }
          if(newUpdate){
            snackbarHostState.showSnackbar("با موفقیت به روز رسانی  شد",duration = SnackbarDuration.Short)
        }

        if (paymentChanged) {
            openPaymentSendSMSDialog.value=true

        }
        if (statusChanged &&  currentReceipt.value.status!=1 &&currentReceipt.value.status!=0) {
            openStatusSendSMSDialog.value=true

        }

    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.restartState()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getReceiptById(
            receiptId!!,
            snackbarHostState
        )
    }

    NewReceiptCreatorTheme(
        displayProgressBar = loading,
        themColor = CustomColors.lightBlue
    ) {


        if(openSendSmsDialog.value){
            SendSmsDialog(
                onDismiss = {
                    openSendSmsDialog.value = false
                },
                description = "آیا قصد ارسال رسید پیامکی را دارید؟",
                sendClicked = {


                    viewModel.sendMessage(currentReceipt.value, snackbarHostState)

                    openSendSmsDialog.value = false

                },
                context = context
            )
        }

        if(openPaymentSendSMSDialog.value){
            SendSmsDialog(
                onDismiss = {
                    openPaymentSendSMSDialog.value = false
                },
                description = "آیا قصد ارسال رسید پیامکی در خصوص دریافت وجه را دارید؟",
                sendClicked = {

                    viewModel.paymentSendMessage(
                        snackbarHostState = snackbarHostState,
                        generalReceipt= currentReceipt.value,
                        payedAmount =currentReceipt.value.prepayment!!

                    )

                    openPaymentSendSMSDialog.value = false

                },
                context = context
            )

        }
        if(openStatusSendSMSDialog.value){
            SendSmsDialog(
                onDismiss = {
                    openStatusSendSMSDialog.value = false
                },
                description = "آیا قصد ارسال  پیامک در خصوص تغییر وضعیت را دارید؟",
                sendClicked = {

                    viewModel.sendMessage(currentReceipt.value,snackbarHostState)

                    openStatusSendSMSDialog.value = false

                },
                context = context
            )
        }


        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopBar(
                    onBackClicked = {
                        navController.popBackStack()
                    }
                )
            },
            bottomBar = {
                BottomBar(
                    itemClicked = {
                        when (it) {

                            Screen.EditReceipt.route -> {
                                val route = it + "/${receiptId}"
                                navController.navigate(route = route) {
                                    popUpTo(Screen.Home.route) {
                                        inclusive = false
                                    }
                                }
                            }

                            "delete" -> {
                                openDeleteDialog.value = true

                            }

                        }

                    }
                )
            }
        ) {
            if (openDeleteDialog.value) {

                CustomAcceptDialog(
                    onDismiss = {
                        openDeleteDialog.value = false
                    },
                    onAccept = {
                        viewModel.deleteReceipt(
                            receiptId = receiptId!!,
                            snackbarHostState = snackbarHostState
                        )
                        openDeleteDialog.value = false

                    },
                    title = "حذف رسید",
                    description = "آیا از حذف این رسید مطمِئنید؟"
                )

            }
            Column(
                modifier = Modifier
                    .background(CustomColors.lightBlue)
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, top = it.calculateTopPadding())
            ) {


                ReceiptCard(
                    modifier = Modifier
                        .padding(horizontal = 25.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    receiptCategory = receiptCategory,
                    generalReceipt = currentReceipt.value
                )


            }


        }


    }

}





