package com.saeed.zanjan.receipt.presentation.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.interactor.Backup
import com.saeed.zanjan.receipt.interactor.ExportExcelFile
import com.saeed.zanjan.receipt.interactor.ListOfReceipts
import com.saeed.zanjan.receipt.utils.CsvExportUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject


@HiltViewModel
class HomeViewModel
   @Inject constructor(
       private val  sharedPreferences: SharedPreferences,
       private val lisOfReceipts: ListOfReceipts,
       private val backup: Backup,
       private val exportExcelFile: ExportExcelFile
   ) :ViewModel() {


        val receiptCategory=1//sharedPreferences.getInt("JOB_SUBJECT",-1)
    val loading = mutableStateOf(false)
    val receiptList: MutableState<List<GeneralReceipt>> = mutableStateOf(ArrayList())
    val databaseSaved= mutableStateOf(false)


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


    fun searchReceipt(query:String,snackbarHostState: SnackbarHostState){
        lisOfReceipts.searchReceipt(query,receiptCategory).onEach { dataState ->

            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                receiptList.value=it
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)

            }


        }.launchIn(viewModelScope)
    }

    fun filterReceipt(status:Int,snackbarHostState: SnackbarHostState){
        lisOfReceipts.filterReceipts(status,receiptCategory).onEach { dataState ->

            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                receiptList.value=it
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)

            }


        }.launchIn(viewModelScope)

    }

    fun commentOnApp(context: Context){
        val intent = Intent(Intent.ACTION_EDIT)
        intent.data =
            Uri.parse("bazaar://details?id=" + "com.saeed.zanjan.receipt")
        intent.setPackage("com.farsitel.bazaar")
        context.startActivity(intent)
    }

    //TODO storage permissin
    fun uploadBackUpOfDatabase(snackbarHostState: SnackbarHostState){
        backup.backupDb().onEach { dataState ->
            dataState.loading.let {
                loading.value=it
            }
            dataState.data?.let {
                snackbarHostState.showSnackbar("done")
            }
            dataState.error?.let {
                snackbarHostState.showSnackbar(it)

            }
        }.launchIn(viewModelScope)

    }

    //TODO storage permissin
    fun exportExcel(snackbarHostState: SnackbarHostState){
        exportExcelFile.databaseExport(receiptCategory).onEach { dataState ->
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



   }
