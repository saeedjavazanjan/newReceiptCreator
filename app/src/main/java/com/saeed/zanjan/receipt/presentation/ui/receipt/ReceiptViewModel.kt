package com.saeed.zanjan.receipt.presentation.ui.receipt

import android.Manifest
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.view.View
import android.widget.FrameLayout
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.interactor.ListOfReceipts
import com.saeed.zanjan.receipt.interactor.ReceiptQueryInDatabase
import com.saeed.zanjan.receipt.interactor.SendSms
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class ReceiptViewModel
@Inject constructor(
    val listOfReceipts: ListOfReceipts,
    val receiptQueryInDatabase: ReceiptQueryInDatabase,
    val sharedPreferences: SharedPreferences,
    val sendSms: SendSms
) : ViewModel() {

    val receiptCategory = 1//sharedPreferences.getInt("JOB_SUBJECT",-1)
    val loading = mutableStateOf(false)
    val deleteState = mutableStateOf(false)

    val currentReceipt = mutableStateOf(GeneralReceipt())
/*    fun requestStoragePermissionsAndShare(context: Context, content: @Composable () -> Unit) {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            // Use a method to request permissions and then proceed with sharing the image
            viewModelScope.launch {
                // Assuming permissions are granted here, handle the actual permission request as needed
                withContext(Dispatchers.Main) {
                 //   shareReceiptCardImage(context, content)
                }
            }
        } else {
            viewModelScope.launch(Dispatchers.Main) {
               // shareReceiptCardImage(context, content)
            }
        }
    }

     suspend fun shareReceiptCardImage(context: Context, content: @Composable () -> Unit) {
        val bitmap = getBitmapFromComposable(context, content)
        val file = saveBitmapToFile(bitmap, "my_receipt_card_image")
        file?.let {
            shareImage(context, it)
        }
    }

    private suspend fun getBitmapFromComposable(context: Context, content: @Composable () -> Unit): Bitmap? {
        return suspendCancellableCoroutine { continuation ->
            ScreenshotContentHolder.content = content
            val intent = ScreenshotActivity.createIntent(context)
            context.startActivity(intent)
            continuation.invokeOnCancellation {
                ScreenshotContentHolder.bitmap = null
            }
            viewModelScope.launch(Dispatchers.Main) {
                while (ScreenshotContentHolder.bitmap == null) {
                    // Wait until the bitmap is set by ScreenshotActivity
                }
                continuation.resume(ScreenshotContentHolder.bitmap)
            }
        }
    }

    private fun saveBitmapToFile(bitmap: Bitmap?, fileName: String): File? {
        if (bitmap == null) return null

        val directory = File(Environment.getExternalStorageDirectory().toString() + "/ComposeScreenshots")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, "$fileName.png")
        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return file
    }

    private fun shareImage(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Share Image"))
    }*/
    fun getReceiptById(
        receiptId: Int,
        snackbarHostState: SnackbarHostState

    ) {

        listOfReceipts.getReceiptById(
            receiptId,
            receiptCategory
        ).onEach { dataState ->

            dataState.loading.let {
                loading.value = it
            }
            dataState.data?.let {
                currentReceipt.value = it
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)
            }


        }.catch {
            loading.value = false
            snackbarHostState.showSnackbar(it.message.toString() ?: "خطای ناشناخته")
        }.launchIn(viewModelScope)


    }


    fun sendMessage(
        generalReceipt: GeneralReceipt,
        snackbarHostState: SnackbarHostState
    ) {
        sendSms.receiptSendSMS(
            generalReceipt,
            receiptCategory
        ).onEach { dataState ->

            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                snackbarHostState.showSnackbar(it,duration = SnackbarDuration.Short)
            }

            dataState.error?.let {
                snackbarHostState.showSnackbar(it,duration = SnackbarDuration.Short)

            }

        }.launchIn(viewModelScope)

    }

    fun paymentSendMessage(
        snackbarHostState: SnackbarHostState,
        generalReceipt: GeneralReceipt,
        payedAmount:String
    ) {
        sendSms.paymentSendSMS(
            generalReceipt,
            payedAmount
        ).onEach { dataState ->

            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                snackbarHostState.showSnackbar(it,duration = SnackbarDuration.Short)
            }

            dataState.error?.let {
                snackbarHostState.showSnackbar(it,duration = SnackbarDuration.Short)

            }

        }.launchIn(viewModelScope)

    }



    fun deleteReceipt(
        receiptId: Int,
        snackbarHostState: SnackbarHostState
    ) {

        receiptQueryInDatabase.deleteReceipt(
            receiptId,
            receiptCategory
        ).onEach { dataState ->

            dataState.loading.let {
                loading.value = it
            }
            dataState.data?.let {
                snackbarHostState.showSnackbar(it,duration = SnackbarDuration.Short)
                deleteState.value=true
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it,duration = SnackbarDuration.Short)

            }


        }.launchIn(viewModelScope)
    }

    fun restartState(){

        deleteState.value=false
    }



}
