package com.saeed.zanjan.receipt.presentation.ui.splash

import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.presentation.components.AddReceiptCard
import com.saeed.zanjan.receipt.presentation.components.HomeTopBar
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navigateToHome:()->Unit
){
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope= rememberCoroutineScope()
    val context= LocalContext.current
    val loading = viewModel.loading.value
    LaunchedEffect(Unit){
        viewModel.downloadDb(snackbarHostState)

    }

    if(viewModel.databaseSaved.value){
        navigateToHome()
    }


    NewReceiptCreatorTheme(
        displayProgressBar = loading,
        themColor = Color.Transparent
    ) {


        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {

            },
            bottomBar = {

            }

        ) {

            Text(text = "Splash", modifier = Modifier.padding(it.calculateTopPadding()))

        }
    }

}