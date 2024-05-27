package com.saeed.zanjan.receipt.presentation.ui.create_receipt

import android.content.SharedPreferences
import android.telephony.SmsManager
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import com.saeed.zanjan.receipt.interactor.SaveReceiptInDatabase
import com.saeed.zanjan.receipt.interactor.SendSms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateReceiptViewModel
@Inject constructor(
    val saveReceiptInDatabase: SaveReceiptInDatabase,
    val sendSms: SendSms,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val receiptCategory=1//sharedPreferences.getInt("JOB_SUBJECT",-1)

    val loading = mutableStateOf(false)
    val dataSaveStatus = mutableStateOf(false)
    val dataSaveStatusForSMS = mutableStateOf(false)

    val smsSenState = mutableStateOf("")

    fun saveInDatabase(
        generalReceipt: GeneralReceipt,
        snackbarHostState: SnackbarHostState,
    ) {
        saveReceiptInDatabase.saveRepairReceipt(generalReceipt,receiptCategory).onEach { dataState ->

            dataState.loading.let {
                loading.value = it
            }
            dataState.data?.let {
                dataSaveStatus.value = true
                snackbarHostState.showSnackbar("با موفقیت ذخیره شد")

                dataSaveStatusForSMS.value = true
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)
            }

        }.launchIn(viewModelScope)


    }

     fun sendMessage(
        generalReceipt: GeneralReceipt,
    ) {
        sendSms.repairSendSMS(
            generalReceipt,
            receiptCategory
        ).onEach { dataState ->

            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                smsSenState.value=it

            }

            dataState.error?.let {
                smsSenState.value=it

            }

        }.launchIn(viewModelScope)

    }
fun restartState(){
    dataSaveStatus.value=false
    dataSaveStatusForSMS.value=false
}
}