package com.saeed.zanjan.receipt.presentation.ui.receipt

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt

@Composable
fun ReceiptCardForPrint(
   generalReceipt: GeneralReceipt
){

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = generalReceipt.name)
        generalReceipt.deliveryTime?.let { Text(text = it) }
        generalReceipt.cost?.let { Text(text = it) }
        Text(text = generalReceipt.name)
        Text(text = generalReceipt.name)
    }
}