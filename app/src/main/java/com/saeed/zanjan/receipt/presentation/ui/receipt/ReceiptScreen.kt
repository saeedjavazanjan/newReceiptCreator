package com.saeed.zanjan.receipt.presentation.ui.receipt

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Picture
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.saeed.zanjan.receipt.presentation.components.BluetoothDevicesDialog
import com.saeed.zanjan.receipt.presentation.components.BottomBar
import com.saeed.zanjan.receipt.presentation.components.CustomAcceptDialog
import com.saeed.zanjan.receipt.presentation.components.OtpCodeShowDialog
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
    newSaved: Boolean = false,
    newUpdate: Boolean = false,
    statusChanged: Boolean = false,
    paymentChanged: Boolean = false,
) {
    val receiptCategory = viewModel.receiptCategory

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val view = LocalView.current
    val picture = remember { Picture() }

    var currentReceipt = viewModel.currentReceipt

    val loading = viewModel.loading.value
    val deleteState = viewModel.deleteState.value


    val openDeleteDialog = remember { mutableStateOf(false) }
    val openSendSmsDialog = remember { mutableStateOf(false) }
    val openStatusSendSMSDialog = remember { mutableStateOf(false) }
    val openPaymentSendSMSDialog = remember { mutableStateOf(false) }
    val openSendOtpCodeDialog = remember { mutableStateOf(false) }
    val openOtpCodeShowDialog = remember { mutableStateOf(false) }

    var hasBluetoothPermission by remember { mutableStateOf(false) }
    var hasSmsPermission by remember { mutableStateOf(false) }
    var hasStoragePermission by remember { mutableStateOf(false) }
    var shouldShowRationale by remember { mutableStateOf(false) }
    var btScanStatus by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {

            hasSmsPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED

        hasStoragePermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.WRITE_EXTERNAL_STORAGE,

            ) == PackageManager.PERMISSION_GRANTED
        hasBluetoothPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.BLUETOOTH,

            ) == PackageManager.PERMISSION_GRANTED


    }
    val requestSmsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            hasSmsPermission = true
        } else {
            shouldShowRationale = true
        }
    }
    val requestStoragePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            hasStoragePermission = true
        } else {
            shouldShowRationale = true
        }
    }

    val requestBluetoothPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val blueToothManager: BluetoothManager =
                context.getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter: BluetoothAdapter? = blueToothManager.adapter
            hasBluetoothPermission = true
            if (bluetoothAdapter?.isEnabled == false) {

            } else {

                btScanStatus = true
            }

        } else {
            shouldShowRationale = true
        }
    }

    val btActivityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            btScanStatus = true
        }

    }

    if (shouldShowRationale) {
        Dialog(onDismissRequest = { shouldShowRationale = false }) {
            Surface(
                modifier = Modifier.widthIn(max = 400.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "نیاز به مجوز!!",
                        style = MaterialTheme.typography.titleLarge,
                        color = CustomColors.darkPurple
                    )

                    Text(
                        textAlign = TextAlign.Start,
                        text = " برنامه نیاز به مجوز دسترسی به حافظه دارد لطفا با کلیک روی دکمه Allow مجوز را تایید کنید",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )

                    Row {
                        TextButton(
                            onClick = {
                                shouldShowRationale = false
                                requestStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                            }

                        ) {
                            Text(
                                text = "تلاش مجدد",
                                style = MaterialTheme.typography.bodyLarge,
                                color = CustomColors.darkPurple
                            )
                        }
                        Spacer(modifier = Modifier.size(30.dp))
                        TextButton(
                            onClick = {
                                shouldShowRationale = false
                            }

                        ) {
                            Text(
                                text = "انصراف",
                                style = MaterialTheme.typography.bodyLarge,
                                color = CustomColors.darkPurple
                            )
                        }
                    }
                }
            }
        }


    }




    LaunchedEffect(key1 = deleteState) {
        if (deleteState)
            navController.popBackStack()
    }


    LaunchedEffect(newSaved) {
        if (newSaved) {
            snackbarHostState.showSnackbar("با موفقیت ذخیره شد", duration = SnackbarDuration.Short)
            openSendSmsDialog.value = true
        }
        if (newUpdate) {
            snackbarHostState.showSnackbar(
                "با موفقیت به روز رسانی  شد",
                duration = SnackbarDuration.Short
            )
        }

        if (paymentChanged) {
            openPaymentSendSMSDialog.value = true

        }
        if (statusChanged && currentReceipt.value.status != 1 && currentReceipt.value.status != 0) {
            openStatusSendSMSDialog.value = true

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


        if (btScanStatus) {

            btScan(
                context = context,
                print = {
                    viewModel.blueToothConnectionClass.setPrinterName(it)
                    viewModel.print(context = context, snackbarHostState = snackbarHostState)
                },
                close = {
                    btScanStatus = false
                }


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

                            "share" -> {
                                if (!hasStoragePermission) {
                                    requestStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                } else {
                                    viewModel.shareReceiptImage(
                                        picture = picture,
                                        context = context,
                                        snackbarHostState = snackbarHostState
                                    )

                                }

                            }

                            "print" -> {
                                if (hasBluetoothPermission) {
                                    val enableBtIntent =
                                        Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                                    btActivityResultLauncher.launch(enableBtIntent)
                                } else {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                        requestBluetoothPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)

                                    } else {
                                        requestBluetoothPermissionLauncher.launch(Manifest.permission.BLUETOOTH_ADMIN)

                                    }
                                }
                            }

                            "sendCode" -> {
                                openSendOtpCodeDialog.value = true

                            }
                        }

                    }
                )
            }
        ) {

            if (openSendSmsDialog.value) {
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

            if (openPaymentSendSMSDialog.value) {
                SendSmsDialog(
                    onDismiss = {
                        openPaymentSendSMSDialog.value = false
                    },
                    description = "آیا قصد ارسال رسید پیامکی در خصوص دریافت وجه را دارید؟",
                    sendClicked = {

                        viewModel.paymentSendMessage(
                            snackbarHostState = snackbarHostState,
                            generalReceipt = currentReceipt.value,
                            payedAmount = currentReceipt.value.prepayment!!

                        )

                        openPaymentSendSMSDialog.value = false

                    },
                    context = context
                )

            }
            if (openStatusSendSMSDialog.value) {
                SendSmsDialog(
                    onDismiss = {
                        openStatusSendSMSDialog.value = false
                    },
                    description = "آیا قصد ارسال  پیامک در خصوص تغییر وضعیت را دارید؟",
                    sendClicked = {

                        viewModel.sendMessage(currentReceipt.value, snackbarHostState)

                        openStatusSendSMSDialog.value = false

                    },
                    context = context
                )
            }

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
                    description = "آیا از حذف این رسید مطمِئنید؟",
                    acceptText = "تایید"
                )

            }

            if (openSendOtpCodeDialog.value) {
                CustomAcceptDialog(
                    onDismiss = {
                        openSendOtpCodeDialog.value = false

                    },
                    onAccept = {
                        if (!hasSmsPermission) {
                            requestSmsPermissionLauncher.launch(Manifest.permission.SEND_SMS)
                        }else{
                            viewModel.generateAndSendOtpPassword(snackbarHostState = snackbarHostState)
                            openOtpCodeShowDialog.value = true
                            openSendOtpCodeDialog.value = false
                        }


                    },
                    title = "رمز تحویل کالا",
                    description = "جهت اطمینان از هویت تحویل گیرنده کالا می توانید یک رمز یکبار مصرف به شماره مشتری ارسال کنید و در صورت مطابقت رمز، کالا را تحویل دهید.",
                    acceptText = "ارسال"
                )

            }

            if (openOtpCodeShowDialog.value) {

                OtpCodeShowDialog(
                    onDismiss = {
                        openOtpCodeShowDialog.value = false
                    },

                    code = viewModel.otpCode.value
                )
            }


            Column(
                modifier = Modifier
                    .background(CustomColors.lightBlue)
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp)
                    .drawWithCache {
                        // Example that shows how to redirect rendering to an Android Picture and then
                        // draw the picture into the original destination
                        val width = this.size.width.toInt()
                        val height = this.size.height.toInt()
                        onDrawWithContent {
                            val pictureCanvas =
                                androidx.compose.ui.graphics.Canvas(
                                    picture.beginRecording(
                                        width,
                                        height
                                    )
                                )
                            draw(this, this.layoutDirection, pictureCanvas, this.size) {
                                this@onDrawWithContent.drawContent()
                            }
                            picture.endRecording()

                            drawIntoCanvas { canvas -> canvas.nativeCanvas.drawPicture(picture) }
                        }
                    }
            ) {
                ReceiptCardForShare(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    receiptCategory = receiptCategory,
                    generalReceipt = currentReceipt.value
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

@Composable
@SuppressLint("MissingPermission")
fun btScan(context: Context, print: (String) -> Unit, close: () -> Unit) {
    var openBtDevicesList by remember {
        mutableStateOf(true)
    }

    val blueToothManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = blueToothManager.adapter
    val pairedDevices: Set<BluetoothDevice> =
        bluetoothAdapter?.bondedDevices as Set<BluetoothDevice>
    var data: MutableList<MutableMap<String?, Any?>?>? = null
    data = ArrayList()

    if (pairedDevices.isNotEmpty()) {
        val dataNum1: MutableMap<String?, Any?> = HashMap()
        dataNum1["A"] = ""
        dataNum1["B"] = ""
        data.add(dataNum1)
        for (device in pairedDevices) {
            val dataNum: MutableMap<String?, Any?> = HashMap()
            dataNum["A"] = device.name
            dataNum["B"] = device.address
            data.add(dataNum)
        }
        if (openBtDevicesList) {
            BluetoothDevicesDialog(
                onDismiss = {
                    openBtDevicesList = false
                    close()
                },
                devices = data,
                itemClicked = {
                    openBtDevicesList = false
                    print(it)
                    close()
                }


            )
        }


    } else {
        Toast.makeText(context, "notdevice found", Toast.LENGTH_SHORT).show()
    }
}

