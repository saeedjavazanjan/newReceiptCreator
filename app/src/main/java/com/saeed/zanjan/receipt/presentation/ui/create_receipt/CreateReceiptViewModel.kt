package com.saeed.zanjan.receipt.presentation.ui.create_receipt

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.RepairsReceipt
import com.saeed.zanjan.receipt.interactor.SaveReceiptInDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateReceiptViewModel
@Inject  constructor (
    val saveReceiptInDatabase: SaveReceiptInDatabase
) :ViewModel() {

    val loading= mutableStateOf(false)

    fun saveInDatabase(
        repairsReceipt: RepairsReceipt,
        snackbarHostState: SnackbarHostState,
    ){
        saveReceiptInDatabase.
        saveRepairReceipt(repairsReceipt).onEach {dataState ->

        dataState.loading.let {
            loading.value=it
        }
        dataState.data?.let {
            snackbarHostState.showSnackbar("با موفقیت ذخیره شد")

        }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)
            }

        }.launchIn(viewModelScope)


    }


}