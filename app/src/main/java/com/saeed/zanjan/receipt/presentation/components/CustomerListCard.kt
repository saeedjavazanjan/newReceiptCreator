package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.domain.models.Customer
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
@Composable
fun CustomerListCard(
    customer: Customer,
    onDelete:(Int)->Unit={}
) {

    var offset by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    offset = (offset + dragAmount).coerceIn(-200f, 0f)
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp)
                .offset(x = offset.dp)
                .animateContentSize()
        ) {
            Checkbox(
                checked = false,
                onCheckedChange = {

                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(customer.name!!, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(customer.phoneNumber!!, fontSize = 14.sp, color = Color.Gray)
                Text(customer.dept.toString(), fontSize = 14.sp, color = Color.Gray)
            }
        }
        if (offset == -200f) {
            IconButton(
                onClick = { onDelete(1) },
                modifier = Modifier.align(Alignment.CenterEnd).padding(8.dp)
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }

}