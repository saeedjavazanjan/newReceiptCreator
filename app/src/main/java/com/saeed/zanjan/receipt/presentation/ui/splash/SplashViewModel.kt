package com.saeed.zanjan.receipt.presentation.ui.splash

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.saeed.zanjan.receipt.interactor.Backup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        val backup: Backup
    )
    :ViewModel() {

    val loading= mutableStateOf(false)
    val databaseSaved= mutableStateOf(false)


        fun downloadDb(snackbarHostState: SnackbarHostState){
            backup.downloadDatabase().onEach { dataState ->

                dataState.loading.let {
                    loading.value=it


                }
                dataState.data?.let{
                    databaseSaved.value=true
                    snackbarHostState.showSnackbar(it)
                }
                dataState.error?.let {
                    snackbarHostState.showSnackbar(it)


                }

            }.launchIn(viewModelScope)

        }

}