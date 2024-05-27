package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@Composable
fun AddReceiptCard(
    addButtonClicked:()->Unit,
    modifier: Modifier
){
    Card(
        modifier=modifier,
        elevation= CardDefaults.cardElevation(20.dp),
        shape= CircleShape,
        colors= CardDefaults.cardColors(
            containerColor = CustomColors.lightBlue,
            contentColor = CustomColors.lightBlue
        ),


        ) {
        // Card content: Name and circular image
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {

            Text(
                modifier = Modifier.padding(10.dp),
                text =  "افزودن رسید جدید",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))

            Button(
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomColors.darkBlue
                ),
                onClick = {
                    addButtonClicked()
                },
            ) {
                Icon(
                    Icons.Default.Add,
                    tint = Color.White,
                    contentDescription = null
                )
            }


        }
    }
}