package com.saeed.zanjan.receipt.presentation.ui.receipt

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Picture
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.interactor.BlueToothConnectionClass
import com.saeed.zanjan.receipt.interactor.ListOfReceipts
import com.saeed.zanjan.receipt.interactor.ReceiptQueryInDatabase
import com.saeed.zanjan.receipt.interactor.SendSms
import com.saeed.zanjan.receipt.interactor.ShareReceipt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel
@Inject constructor(
    val listOfReceipts: ListOfReceipts,
    val receiptQueryInDatabase: ReceiptQueryInDatabase,
    val sharedPreferences: SharedPreferences,
    val shareReceipt: ShareReceipt,
    val sendSms: SendSms,
    val blueToothConnectionClass: BlueToothConnectionClass
) : ViewModel() {

    val receiptCategory = sharedPreferences.getInt("JOB_SUBJECT",-1)
    val loading = mutableStateOf(false)
    val deleteState = mutableStateOf(false)
    val otpCode = mutableStateOf(0)

    val currentReceipt = mutableStateOf(GeneralReceipt())
    var data:MutableList<MutableMap<String?,Any?>?>?=null

    fun shareReceiptImage(
        picture: Picture,
        context: Context,
        snackbarHostState: SnackbarHostState
    ){
        shareReceipt.shareReceipt(
            picture,context
        ).onEach { dataState ->
            dataState.loading.let {
                loading.value=it
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            }

        }.launchIn(viewModelScope)
    }



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


    fun print(context: Context,snackbarHostState: SnackbarHostState){
        blueToothConnectionClass.intentPrint(currentReceipt.value, context = context).onEach { dataState ->

            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                snackbarHostState.showSnackbar(it)

            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)
            }

        }.launchIn(viewModelScope)
    }

    fun generateAndSendOtpPassword(snackbarHostState: SnackbarHostState){

        otpCode.value=createFourDigitNumber()

        sendSms.sendOtpSms(currentReceipt.value,otpCode.value).onEach { dataState ->

        dataState.loading.let {
            loading.value=it
        }
            dataState.data?.let {


            }
        dataState.error?.let {
            snackbarHostState.showSnackbar(it)
        }


        }.launchIn(viewModelScope)



    }
    fun createFourDigitNumber(): Int {
        var fourDigitNumber  = ""
        val rangeList = {(0..9).random()}

        while(fourDigitNumber.length < 4)
        {
            val num = rangeList().toString()
            if (!fourDigitNumber.contains(num)) fourDigitNumber +=num
        }

        return fourDigitNumber.toInt()
    }

    fun restartState(){

        deleteState.value=false
    }




}
