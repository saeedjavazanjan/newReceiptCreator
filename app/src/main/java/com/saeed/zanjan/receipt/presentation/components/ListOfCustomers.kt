package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.domain.models.Customer
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt

@Composable
fun ListOfCustomers(
    modifier: Modifier,
    receiptCategory: Int,
    customers: List<Customer>,
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier
    ) {
        itemsIndexed(
            items = customers
        ) { index, cus ->

            CustomerListCard(cus)

        }
        item {
            Spacer(modifier = Modifier.height(100.dp)) // Adjust the height based on BottomAppBar height
        }
    }

}