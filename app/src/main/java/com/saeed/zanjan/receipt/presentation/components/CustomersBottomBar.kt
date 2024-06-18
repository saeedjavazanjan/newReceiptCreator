package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@Composable
fun CustomersBottomBar(
    cardClick:()->Unit
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .padding(30.dp)
                .clickable {
                    cardClick()
                },
            elevation = CardDefaults.cardElevation(20.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = CustomColors.lightBlue,
                contentColor = CustomColors.lightBlue
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "متن پیامک",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium

                )

                Spacer(modifier = Modifier.size(30.dp))

                Icon(
                    painter = painterResource(id = R.drawable.about_us),
                    tint = Color.White,
                    contentDescription = null
                )

            }
        }
    }

}