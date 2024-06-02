package com.saeed.zanjan.receipt.presentation.ui.receipt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Environment
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.interactor.ListOfReceipts
import com.saeed.zanjan.receipt.interactor.ReceiptQueryInDatabase
import com.saeed.zanjan.receipt.interactor.SendSms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

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

    fun saveBitmapToFile(bitmap: Bitmap, fileName: String): File? {
        val directory = File(Environment.getExternalStorageDirectory().toString() + "/receiptCreator")
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

    fun shareImage(file: File,context:Context) {
        val uri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Share Image"))
    }

}
