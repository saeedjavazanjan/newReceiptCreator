package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@Composable
fun StatusDialog(
    onDismiss: () -> Unit,
    onStatusSelected:(Int)->Unit
    ){
    Dialog(
        onDismissRequest = onDismiss,
        ) {
        Surface(
            modifier = Modifier.widthIn(max = 400.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier=Modifier.fillMaxWidth().clickable {
                                                               onStatusSelected(3)
                    },
                    elevation= CardDefaults.cardElevation(10.dp),
                    shape= CircleShape,
                    colors= CardDefaults.cardColors(
                        containerColor = CustomColors.readyForDelivery,
                        contentColor = CustomColors.readyForDelivery
                    ),


                    ) {
                    Row(
                        verticalAlignment=Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ready_to_delivery_icon),
                            tint = Color.White,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(10.dp))

                        Text(text = "آماده تحویل",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                    
                    
                }
                Card(
                    modifier=Modifier.fillMaxWidth().clickable {
                        onStatusSelected(0)

                    },
                    elevation= CardDefaults.cardElevation(10.dp),
                    shape= CircleShape,
                    colors= CardDefaults.cardColors(
                        containerColor = CustomColors.inProses,
                        contentColor = CustomColors.inProses
                    ),


                    ) {
                    Row(
                        verticalAlignment=Alignment.CenterVertically,

                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.inproses_icon),
                            tint = Color.White,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(10.dp))

                        Text(text = "در حال انجام",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
                Card(
                    modifier=Modifier.fillMaxWidth().clickable {
                        onStatusSelected(2)

                    },
                    elevation= CardDefaults.cardElevation(10.dp),
                    shape= CircleShape,
                    colors= CardDefaults.cardColors(
                        containerColor = CustomColors.problem,
                        contentColor = CustomColors.problem
                    ),


                    ) {
                    Row(
                        verticalAlignment=Alignment.CenterVertically,

                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.problem_icon),
                            tint = Color.White,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(10.dp))

                        Text(text = "مشکل در روند سفارش",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
                Card(
                    modifier=Modifier.fillMaxWidth().clickable {
                        onStatusSelected(1)

                    },
                    elevation= CardDefaults.cardElevation(10.dp),
                    shape= CircleShape,
                    colors= CardDefaults.cardColors(
                        containerColor = CustomColors.delivered,
                        contentColor = CustomColors.delivered
                    ),


                    ) {
                    Row(
                        verticalAlignment=Alignment.CenterVertically,

                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.deliverd_icon),
                            tint = Color.White,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(text = "تحویل داده شده",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }

            }
        }
    }
}