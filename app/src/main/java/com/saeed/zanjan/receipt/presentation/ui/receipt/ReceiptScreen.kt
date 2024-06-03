package com.saeed.zanjan.receipt.presentation.ui.receipt

import HorizontalDashedLine
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

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
    val view = LocalView.current
    val picture = remember { Picture() }

    var currentReceipt = viewModel.currentReceipt

    val loading = viewModel.loading.value
    val deleteState = viewModel.deleteState.value


    val openDeleteDialog = remember { mutableStateOf(false) }
    val openSendSmsDialog = remember { mutableStateOf(false) }
    val openStatusSendSMSDialog = remember { mutableStateOf(false) }
    val openPaymentSendSMSDialog = remember { mutableStateOf(false) }

    val shareClicked = remember { mutableStateOf(false) }


    var hasStoragePermission by remember { mutableStateOf(false) }
    var shouldShowRationale by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {

            hasStoragePermission = ContextCompat.checkSelfPermission(
                context,Manifest.permission.WRITE_EXTERNAL_STORAGE,

                ) == PackageManager.PERMISSION_GRANTED


    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            hasStoragePermission = true
        } else {
            shouldShowRationale = true
        }
    }

    if (shouldShowRationale) {
        Dialog(onDismissRequest =  {shouldShowRationale=false }) {
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
                                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)

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
                            "share"->{
                                if (!hasStoragePermission) {
                                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                }else{
                                        shareClicked.value=true

                                    coroutineScope.launch(Dispatchers.IO) {
                                        delay(100)
                                        val bitmap = createBitmapFromPicture(picture)
                                        val uri = bitmap.saveToDisk(context)
                                        shareBitmap(context, uri)
                                    }
                                }




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
                            .fillMaxSize().padding(10.dp),
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

private fun createBitmapFromPicture(picture: Picture): Bitmap {
    val bitmap = Bitmap.createBitmap(
        picture.width,
        picture.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    canvas.drawColor(CustomColors.lightBlue.hashCode())
    canvas.drawPicture(picture)
    return bitmap
}

 private suspend fun Bitmap.saveToDisk(context: Context): Uri {
    val file = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "screenshot-${System.currentTimeMillis()}.png"
    )

    file.writeBitmap(this, Bitmap.CompressFormat.PNG, 100)

    return scanFilePath(context, file.path) ?: throw Exception("File could not be saved")
}

/**
 * We call [MediaScannerConnection] to index the newly created image inside MediaStore to be visible
 * for other apps, as well as returning its [MediaStore] Uri
 */
private suspend fun scanFilePath(context: Context, filePath: String): Uri? {
    return suspendCancellableCoroutine { continuation ->
        MediaScannerConnection.scanFile(
            context,
            arrayOf(filePath),
            arrayOf("image/png")
        ) { _, scannedUri ->
            if (scannedUri == null) {
                continuation.cancel(Exception("File $filePath could not be scanned"))
            } else {
                continuation.resume(scannedUri)
            }
        }
    }
}

private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}

private fun shareBitmap(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(context, createChooser(intent, "Share your image"), null)
}

