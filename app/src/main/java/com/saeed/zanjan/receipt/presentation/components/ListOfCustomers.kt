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
    customers: List<Customer>,
    onDelete:(Int)->Unit,
    onSelect:(Customer)->Unit,
    deSelect:(Customer)->Unit,
    selectAll:Boolean
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier
    ) {
        itemsIndexed(
            items = customers.reversed()
        ) { index, cus ->

            CustomerListCard(
                cus,
                onDelete = {
                           onDelete(it)
                },
                onSelect = {
                           onSelect(it)
                },
                deSelect = {
                    deSelect(it)
                },
                selectAll=selectAll
            )

        }
        item {
            Spacer(modifier = Modifier.height(100.dp)) // Adjust the height based on BottomAppBar height
        }
    }

}