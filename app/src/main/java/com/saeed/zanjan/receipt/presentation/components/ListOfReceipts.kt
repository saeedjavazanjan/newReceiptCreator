package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt

@Composable
fun ListOfReceipts(
    modifier: Modifier,
    receiptCategory: Int,
    receipts: List<GeneralReceipt>,
    navigateToScreen: (Int) -> Unit

) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier
    ) {
        itemsIndexed(
            items = receipts.reversed()
        ) { index, rec ->

            ReceiptListCard(
                receiptCategory = receiptCategory,
                receipt = rec,
                onReceiptClickListener = {
                    navigateToScreen(it)

                }
            )

        }
        item {
            Spacer(modifier = Modifier.height(100.dp)) // Adjust the height based on BottomAppBar height
        }
    }

}