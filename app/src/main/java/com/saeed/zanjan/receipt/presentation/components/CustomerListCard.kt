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
import androidx.compose.material3.CheckboxDefaults
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
    onDelete:(Int)->Unit,
    onSelect:(Customer)->Unit,
    deSelect:(Customer)->Unit
) {

    var offset by remember { mutableStateOf(0f) }
    var checkState by remember { mutableStateOf(false) }
Box(
    modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .background(CustomColors.lightBlue, RoundedCornerShape(16.dp))
    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    offset = (offset - dragAmount).coerceIn(-70f, 0f)
                }
            }
            .offset(x = offset.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CustomColors.lightGray,
            contentColor = CustomColors.darkPurple
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize()
        ) {
            Checkbox(
                checked = checkState,
                onCheckedChange = {
                                  checkState=it
                    if(it){
                        onSelect(customer)
                    }else{
                        deSelect(customer)
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = CustomColors.readyForDelivery,
                    uncheckedColor = CustomColors.lightBlue
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(customer.name!!,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = CustomColors.darkPurple
                )
                Text(customer.phoneNumber!!,
                    fontSize = 14.sp ,
                    color = CustomColors.darkPurple)

                Row {
                    Text(text = "بدهی:  ",
                        fontSize = 14.sp,
                        color = CustomColors.darkPurple)
                    Text(customer.dept.toString(),
                        fontSize = 14.sp,
                        color = CustomColors.darkPurple)
                }

            }
        }

    }
    if (offset  in  -70f..-20f) {
        IconButton(
            onClick = {
                offset=0f
                onDelete(customer.id)
                      },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(8.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.delete),
                tint = Color.White,
                contentDescription = "Delete"
            )
        }
    }

}

}