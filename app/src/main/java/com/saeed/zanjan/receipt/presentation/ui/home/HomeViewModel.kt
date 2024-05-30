package com.saeed.zanjan.receipt.presentation.ui.home

import android.content.SharedPreferences
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.interactor.ListOfReceipts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
   @Inject constructor(
       private val  sharedPreferences: SharedPreferences,
       private val lisOfReceipts: ListOfReceipts
   ) :ViewModel() {


        val receiptCategory=0//sharedPreferences.getInt("JOB_SUBJECT",-1)
    val loading = mutableStateOf(false)
    val receiptList: MutableState<List<GeneralReceipt>> = mutableStateOf(ArrayList())


    fun getListOfReceipts(
        snackbarHostState: SnackbarHostState
    ){
        lisOfReceipts.getListOfReceipts(
            receiptCategory
        ).onEach {dataState ->

            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                receiptList.value=it
            }
            dataState.error?.let {

                snackbarHostState.showSnackbar(it)
            }

        }.catch {

            loading.value=false
            snackbarHostState.showSnackbar(it.message.toString())

        }.launchIn(viewModelScope)



    }

   }
