package com.saeed.zanjan.receipt.presentation.ui.create_receipt

import android.content.SharedPreferences
import android.telephony.SmsManager
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val sendSms: SendSms
) : ViewModel() {

    val loading = mutableStateOf(false)
    val dataSaveStatus = mutableStateOf(false)

    //this will true after snackBar show
    val dataSaveStatusForSMS = mutableStateOf(false)

    fun saveInDatabase(
        repairsReceipt: RepairsReceipt,
        snackbarHostState: SnackbarHostState,
    ) {
        saveReceiptInDatabase.saveRepairReceipt(repairsReceipt).onEach { dataState ->

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

    private fun repairSendMessage(
        repairsReceipt: RepairsReceipt,
    ) {
        sendSms.repairSendSMS(
            repairsReceipt
        ).onEach { dataState ->

            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {

            }

            dataState.error?.let {

            }

        }.launchIn(viewModelScope)

    }

}