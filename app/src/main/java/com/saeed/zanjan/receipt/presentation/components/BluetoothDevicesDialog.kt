package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.saeed.zanjan.receipt.ui.theme.CustomColors


@Composable
fun BluetoothDevicesDialog(
    onDismiss:()->Unit,
    devices:ArrayList<MutableMap<String?,Any?>?>,
    itemClicked:(String)->Unit
) {


    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier
                .width(400.dp)
                .height(600.dp),
            shape = RoundedCornerShape(8.dp)
        ) {

            Column {
                LazyColumn(
                    state = rememberLazyListState(),
                ) {
                    itemsIndexed(
                        items = devices
                    ) { index, device ->

                        Text(

                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                                .clickable {
                                    itemClicked(
                                        device
                                            ?.get("A")
                                            ?.toString() ?: "Unknown"
                                    )
                                },
                            text =device?.get("A")?.toString() ?: "Unknown"
                        )
                    }

                    item{
                        Divider(
                            color = CustomColors.lightGray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .padding(horizontal = 5.dp)
                        )
                        Text(
                            modifier = Modifier.padding( 20.dp),
                            text = "لازم است گوشی شما از قبل با پرینتر همگام سازی شده باشد.",
                            style= MaterialTheme.typography.bodyMedium,
                            color = CustomColors.darkPurple
                        )
                    }
                }
            }



        }
    }
}