package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@Composable
fun BottomBar(
    itemClicked:(String)->Unit
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier= Modifier.padding(20.dp),
            elevation= CardDefaults.cardElevation(20.dp),
            shape= CircleShape,
            colors= CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(6.dp)
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.lightGray
                    ),
                    onClick = {
                        itemClicked(Screen.EditReceipt.route)
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        tint = CustomColors.darkPurple,
                        contentDescription = null
                    )
                }
                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.lightGray
                    ),
                    onClick = {
                              itemClicked("share")
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.share),
                        tint = CustomColors.darkPurple,
                        contentDescription = null
                    )
                }
                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.lightGray
                    ),
                    onClick = {
                              itemClicked("sendCode")
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.key),
                        tint = CustomColors.darkPurple,
                        contentDescription = null
                    )
                }
                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.lightGray
                    ),
                    onClick = {
                        itemClicked("print")

                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.printer),
                        tint = CustomColors.darkPurple,
                        contentDescription = null
                    )
                }
                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.lightGray
                    ),
                    onClick = {

                          itemClicked("delete")
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        tint = CustomColors.darkPurple,
                        contentDescription = null
                    )
                }
            }

        }
    }

}