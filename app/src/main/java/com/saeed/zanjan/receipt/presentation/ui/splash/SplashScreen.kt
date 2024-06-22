package com.saeed.zanjan.receipt.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.presentation.components.CustomAcceptDialog
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navigateTo:(String)->Unit
){
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope= rememberCoroutineScope()
    val context= LocalContext.current

    val loading = viewModel.loading.value
    val version=viewModel.version.value
    val isUpdate=viewModel.isUpdate.value
    val versionGotFromServer=viewModel.versionGotFromServer.value
    val forceToHome=viewModel.forceToHome.value

    val destinationRoute=if (viewModel.dataSaved)Screen.Home.route else Screen.Registration.route

    var openUpdateDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit){
        //viewModel.downloadDb(snackbarHostState)
        viewModel.getAppVersionName(context)
        viewModel.checkVersionFromServer(snackbarHostState)

    }
    LaunchedEffect(key1 = versionGotFromServer, key2 = forceToHome ){
        if(versionGotFromServer && !isUpdate){
            openUpdateDialog=true
        }else if(versionGotFromServer && isUpdate) {
             navigateTo(destinationRoute)
        }else if(forceToHome){
            navigateTo(destinationRoute)

        }
    }

   /* if(viewModel.databaseSaved.value){
        navigateToHome()
    }*/



    NewReceiptCreatorTheme(
        displayProgressBar = false,
        themColor = CustomColors.lightBlue
    ) {


        if(openUpdateDialog){
            CustomAcceptDialog(
                onDismiss = {
                            openUpdateDialog=false
                    navigateTo(destinationRoute)
                            },
                onAccept = {
                    coroutineScope.launch {
                        viewModel.updateApp(context,snackbarHostState)

                    }
                           },
                title ="به روز رسانی" ,
                description ="آیا مایل به به روز رسانی برنامه هستید" ,
                acceptText = "به روز رسانی"
            )
        }

        Scaffold(
            containerColor = CustomColors.lightBlue,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {

            },
            bottomBar = {

            }

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it.calculateTopPadding())
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.receipt_app_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.height(70.dp).fillMaxWidth()
                ) {
                    if(loading){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            color = Color.White
                        )
                    }
                }


                Spacer(modifier = Modifier.weight(1f))

                Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "ورژن $version",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
            }


        }
    }

}