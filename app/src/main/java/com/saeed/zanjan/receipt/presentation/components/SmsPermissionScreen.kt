package com.saeed.zanjan.receipt.presentation.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SmsPermissionScreen() {
    val context = LocalContext.current
    var hasSmsPermission by remember { mutableStateOf(false) }

    // این لانچر برای درخواست مجوز استفاده می‌شود
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasSmsPermission = isGranted
    }

    // بررسی وضعیت مجوز
    LaunchedEffect(Unit) {
        hasSmsPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    // UI
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Check SMS Permission") }
            )
        }
    ) {
        Column {
            if (hasSmsPermission) {
                Text("SMS permission is granted")
            } else {
                Text("SMS permission is not granted")
                Button(onClick = {
                    requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
                }) {
                    Text("Request SMS Permission")
                }
            }
        }
    }
}