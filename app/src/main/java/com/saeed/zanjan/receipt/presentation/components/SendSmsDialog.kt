package com.saeed.zanjan.receipt.presentation.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@Composable
fun SendSmsDialog(
    description:String,
    sendClicked:()->Unit,
    onDismiss: () -> Unit,
    context:Context
    )
{
    var hasSmsPermission by remember { mutableStateOf(false) }
    var shouldShowRationale by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        hasSmsPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            hasSmsPermission = true
        } else {
            shouldShowRationale = true
        }
    }




    Dialog(onDismissRequest = {
       DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    } ) {
        Surface(
            modifier = Modifier.widthIn(max = 400.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "ارسال پیامک",
                    style = MaterialTheme.typography.titleLarge,
                    color = CustomColors.darkPurple
                )

                Text(
                    textAlign = TextAlign.Start,
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )

                Row {
                    TextButton(
                        onClick = {
                            if (!hasSmsPermission) {
                                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
                            }else{
                                sendClicked()
                                onDismiss()
                            }

                        }

                    ) {
                        Text(
                            text = "ارسال",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CustomColors.darkPurple
                        )
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                    TextButton(
                        onClick = {
                            onDismiss()
                        }

                    ) {
                        Text(
                            text = "انصراف",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CustomColors.darkPurple
                        )
                    }
                }
            }
        }
    }
    if (shouldShowRationale) {
        Dialog(onDismissRequest =  onDismiss ) {
            Surface(
                modifier = Modifier.widthIn(max = 400.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "نیاز به مجوز!!",
                        style = MaterialTheme.typography.titleLarge,
                        color = CustomColors.darkPurple
                    )

                    Text(
                        textAlign = TextAlign.Start,
                        text = "برای ارسال پیامک برنامه نیاز به مجوز دارد لطفا با کلیک روی دکمه Allow مجوز را تایید کنید",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )

                    Row {
                        TextButton(
                            onClick = {
                                    shouldShowRationale = false
                                    requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)


                            }

                        ) {
                            Text(
                                text = "تلاش مجدد",
                                style = MaterialTheme.typography.bodyLarge,
                                color = CustomColors.darkPurple
                            )
                        }
                        Spacer(modifier = Modifier.size(30.dp))
                        TextButton(
                            onClick = {
                                shouldShowRationale = false
                            }

                        ) {
                            Text(
                                text = "انصراف",
                                style = MaterialTheme.typography.bodyLarge,
                                color = CustomColors.darkPurple
                            )
                        }
                    }
                }
            }
        }


    }


}