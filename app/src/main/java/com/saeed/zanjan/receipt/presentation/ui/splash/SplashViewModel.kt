package com.saeed.zanjan.receipt.presentation.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.saeed.zanjan.receipt.interactor.Backup
import com.saeed.zanjan.receipt.interactor.CheckVersion
import com.saeed.zanjan.receipt.utils.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val backup: Backup,
        private val checkVersion: CheckVersion,
        private val connectivityManager: ConnectivityManager,
        val sharedPreferences: SharedPreferences

        )
    :ViewModel() {


    //TODO test is update

    val dataSaved = sharedPreferences.getBoolean("SAVED_IN_SERVER",false)

    val loading= mutableStateOf(false)
   val isUpdate= mutableStateOf(false)
   val versionGotFromServer= mutableStateOf(false)
   val forceToHome= mutableStateOf(false)
    val version= mutableStateOf("--")




    fun checkVersionFromServer(
        snackbarHostState: SnackbarHostState
    ){
        checkVersion.checkVersionOnServer(
            isNetworkAvailable =connectivityManager.isNetworkAvailable.value

        ).onEach { dataState ->

        dataState.loading.let {
            loading.value=it
        }
        dataState.data?.let {
            versionGotFromServer.value=true
            isUpdate.value = it==version.value
        }
        dataState.error?.let {
            forceToHome.value=true
         //   snackbarHostState.showSnackbar(it)
        }

        }.launchIn(viewModelScope)



    }

     fun getAppVersionName(context: Context) {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
           version.value= packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            forceToHome.value=true
            e.printStackTrace()
            "Unknown"
        }
    }

    suspend fun updateApp(context: Context,
                          snackbarHostState: SnackbarHostState
                  ) {
        val intent = Intent(Intent.ACTION_EDIT)
        intent.data = Uri.parse("bazaar://details?id=com.saeed.zanjan.receipt")
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            snackbarHostState.showSnackbar("بازار بر روی گوشی شما نصب نیست")
        }


    }

}