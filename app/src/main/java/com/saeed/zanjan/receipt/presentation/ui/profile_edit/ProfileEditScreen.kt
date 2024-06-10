package com.saeed.zanjan.receipt.presentation.ui.profile_edit

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.domain.models.ProfileData
import com.saeed.zanjan.receipt.presentation.components.CustomDropdown
import com.saeed.zanjan.receipt.presentation.components.EditProfileBottomBar
import com.saeed.zanjan.receipt.presentation.components.ExitDialog
import com.saeed.zanjan.receipt.presentation.components.TopBar
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProfileEditScreen(
    viewModel: ProfileEditViewModel,
    navController:NavController
){
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var hasStoragePermission by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        hasStoragePermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.WRITE_EXTERNAL_STORAGE,

            ) == PackageManager.PERMISSION_GRANTED
    }

    val requestStoragePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
            hasStoragePermission = true

    }

    val dataSaveStatus = viewModel.dataSaveStatus.value
    var openExitDialog by remember { mutableStateOf(false) }


    var imageUri by remember { mutableStateOf<Uri?>(viewModel.avatar.value.toUri()) }
    var companyName by remember { mutableStateOf(viewModel.companyName.value) }
    var companyPhone by remember { mutableStateOf(viewModel.companyPhone.value) }
    var companyAddress by remember { mutableStateOf(viewModel.companyAddress.value) }
    var companyLink by remember { mutableStateOf(viewModel.companyLink.value) }
    var jobType by remember { mutableStateOf(viewModel.jobType.value) }
    var companyRules by remember { mutableStateOf(viewModel.companyRules.value) }
    val jobTypes = listOf(
        "تعمیرات موبایل",
        "تعمیرات کامپیوتر",
        "تعمیرات لوازم برقی",
        "خیاطی",
        "جواهر سازی",
        "عکاسی",
        "خشکشویی",
        "قنادی",
        "سایر مشاغل",
    )
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.restartState()
        }
    }

    NewReceiptCreatorTheme(
        displayProgressBar = false,
        themColor = CustomColors.lightBlue
    ) {
        if (openExitDialog) {
            ExitDialog(onDismiss = {

                openExitDialog= false
            },
                onExitClicked = {
                    openExitDialog = false
                    navController.popBackStack()
                }
            )

        }


            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                containerColor = CustomColors.lightBlue,
                topBar = {
                    TopBar(
                        onBackClicked = {
                               if (dataSaveStatus)
                                   navController.popBackStack()
                               else
                                   openExitDialog = true
                        }
                    )
                },
                bottomBar = {

                    EditProfileBottomBar(cardClick = {
                       coroutineScope.launch {
                       val profileData=
                           ProfileData(
                               avatar = imageUri.toString(),
                               companyName = companyName,
                               companyPhone=companyPhone,
                               companyAddress=companyAddress,
                               companyLink=companyLink,
                               companyRules=companyRules,
                               jobType=jobType

                           )
                           viewModel.saveData(profileData = profileData,
                               snackbarHostState=snackbarHostState)
                       }

                    })
                }
            ) {

                Card(
                    modifier = Modifier
                        .padding(
                            start = 25.dp,
                            end = 25.dp,
                            top = it.calculateTopPadding(),
                            bottom = 16.dp
                        )
                        .fillMaxSize(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = CustomColors.lightBlue,
                    )

                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(all=8.dp)
                                .clickable {
                                    if (!hasStoragePermission) {
                                        requestStoragePermissionLauncher.
                                        launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    } else {
                                        launcher.launch("image/*")

                                    }
                                },
                            contentAlignment = Alignment.BottomEnd

                        ){
                            Image(
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(CircleShape)
                            )
                            GlideImage(
                                model = imageUri,
                                loading = placeholder(R.drawable.profile),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop,
                            )
                            Card( modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape),
                                colors = CardDefaults.cardColors(
                                    containerColor = CustomColors.gray,
                                    contentColor = CustomColors.gray
                                )
                            ){
                                Icon(
                                    painter = painterResource(R.drawable.edit_2),
                                    contentDescription = "",
                                    tint = Color.White,
                                )
                            }


                        }


                        Column {
                            Text(
                                modifier = Modifier,
                                text = "نام مجموعه",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                singleLine = true,
                                value =companyName,
                                shape = RoundedCornerShape(30.dp),
                                onValueChange = { name ->
                                    companyName=name
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.user),
                                        tint = CustomColors.gray,
                                        contentDescription = null
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = CustomColors.darkPurple,
                                    containerColor = CustomColors.transparentLightGray,
                                    cursorColor = CustomColors.lightBlue,
                                    focusedBorderColor = CustomColors.lightBlue,
                                    unfocusedBorderColor = CustomColors.lightGray
                                )
                            )

                        }
                        Column {
                            Text(
                                modifier = Modifier,
                                text = "شماره تماس مجموعه",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                singleLine = true,
                                isError =false,
                                shape = RoundedCornerShape(30.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                value = companyPhone,
                                onValueChange = { phone ->
                                    companyPhone=phone
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.call),
                                        tint = CustomColors.gray,
                                        contentDescription = null
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = CustomColors.darkPurple,
                                    containerColor = CustomColors.transparentLightGray,
                                    cursorColor = CustomColors.lightBlue,
                                    focusedBorderColor = CustomColors.lightBlue,
                                    unfocusedBorderColor = CustomColors.lightGray
                                )
                            )

                        }
                        Column {
                            Text(
                                modifier = Modifier,
                                text = "آدرس مجموعه",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = companyAddress,
                                singleLine = true,
                                shape = RoundedCornerShape(30.dp),
                                onValueChange = { address ->
                                    companyAddress=address
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.location),
                                        tint = CustomColors.gray,
                                        contentDescription = null
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = CustomColors.darkPurple,
                                    containerColor = CustomColors.transparentLightGray,
                                    cursorColor = CustomColors.lightBlue,
                                    focusedBorderColor = CustomColors.lightBlue,
                                    unfocusedBorderColor = CustomColors.lightGray
                                )
                            )

                        }
                        Column {
                            Text(
                                modifier = Modifier,
                                text = "لینک ارتباطی",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = companyLink,
                                singleLine = true,
                                shape = RoundedCornerShape(30.dp),
                                onValueChange = { link ->
                                    companyLink=link
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.link),
                                        tint = CustomColors.gray,
                                        contentDescription = null
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = CustomColors.darkPurple,
                                    containerColor = CustomColors.transparentLightGray,
                                    cursorColor = CustomColors.lightBlue,
                                    focusedBorderColor = CustomColors.lightBlue,
                                    unfocusedBorderColor = CustomColors.lightGray
                                )
                            )

                        }
                        CustomDropdown(
                            items =jobTypes,
                            selectedItem = jobType,
                            onItemSelected = { selected ->
                                jobType = selected
                            },
                            label = jobType,
                            modifier = Modifier.fillMaxWidth(),
                            isError = false
                        )
                        Divider(
                            color = CustomColors.lightGray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .padding(horizontal = 5.dp)
                        )

                        Column {
                            Text(
                                modifier = Modifier,
                                text = "قوانین(حد اکثر 5 خط)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clickable {

                                    },
                                singleLine = false,
                                maxLines = 5,
                                value = companyRules,
                                shape = RoundedCornerShape(30.dp),
                                onValueChange = { rules ->
                                    companyRules=rules
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.about_us),
                                        tint = CustomColors.gray,
                                        contentDescription = null
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = CustomColors.darkPurple,
                                    containerColor = CustomColors.transparentLightGray,
                                    cursorColor = CustomColors.lightBlue,
                                    focusedBorderColor = CustomColors.lightBlue,
                                    unfocusedBorderColor = CustomColors.lightGray
                                )
                            )

                        }
                        Spacer(modifier = Modifier.size(90.dp))

                    }
                }


            }




    }





}