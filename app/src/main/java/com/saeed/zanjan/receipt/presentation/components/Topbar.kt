package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@Composable
fun TopBar(
    onBackClicked:()->Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Button(
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomColors.lightGray
            ),
            onClick = {
                onBackClicked()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.right_arrow_2),
                tint = CustomColors.darkPurple,
                contentDescription = null
            )
        }
    }
}