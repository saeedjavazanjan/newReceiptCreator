package com.saeed.zanjan.receipt.presentation.components

import HorizontalDashedLine
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.ui.theme.CustomColors
@Composable
fun ReceiptListCard(
    receiptCategory:Int=0,
    receipt:GeneralReceipt,
    onReceiptClickListener:(Int)->Unit
){

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth().clickable {
                          onReceiptClickListener(receipt.id)
            },
        shape = RoundedCornerShape( 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CustomColors.lightGray,
            contentColor = CustomColors.darkPurple,
        )
    ) {
        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ){

                receipt.receiptTime?.let {
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        text = it,
                        style=MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )
                }
                receipt.deliveryTime?.let {
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        text = it,
                        style=MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )
                }
                Spacer(modifier =Modifier.weight(1f) )

                Card(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp),
                    shape = RoundedCornerShape(bottomStart = 16.dp),
                    colors = CardDefaults.cardColors(

                        containerColor =
                        when(receipt.status){
                            0->{
                                CustomColors.inProses
                            }
                            1->{
                                CustomColors.delivered
                            }
                            2->{
                                CustomColors.problem

                            }
                            3->{
                                CustomColors.readyForDelivery
                            }
                            else->{
                                CustomColors.inProses

                            }
                        }
                    )
                ){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter =
                            when(receipt.status){
                                0->{
                                    painterResource(id = R.drawable.inproses_icon)
                                }
                                1->{
                                    painterResource(id = R.drawable.deliverd_icon)
                                }
                                2->{
                                    painterResource(id = R.drawable.problem_icon)

                                }
                                3->{
                                    painterResource(id = R.drawable.ready_to_delivery_icon)
                                }
                                else->{
                                    painterResource(id = R.drawable.inproses_icon)

                                }
                            },


                            contentDescription =null ,
                            tint = Color.White
                        )
                    }


                }
            }
            Spacer(modifier = Modifier.size(16.dp))

            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text =  receipt.name,
                style=MaterialTheme.typography.bodyLarge,
                color = CustomColors.darkPurple
            )
            Spacer(modifier = Modifier.size(16.dp))
            receipt.phone?.let {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = it,
                    style=MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
            Spacer(modifier = Modifier.size(16.dp))

            receipt.orderName?.let {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = it,
                    style=MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
            Spacer(modifier = Modifier.size(20.dp))


        }





    }
      /*  Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.wrapContentWidth(), contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .height(30.dp)
                        .width(15.dp)
//            .clipToBounds()
                ) {
                    drawArc(
                        color = CustomColors.lightBlue,
                        90f,
                        180f,
                        useCenter = false,
                        //    topLeft = Offset(4.dp.toPx(), 6.dp.toPx()),
                        size = Size(30.dp.toPx(), 30.dp.toPx()),
                    )
                }
            }

            Box(modifier =
            Modifier
                .weight(1f)
                .height(2.dp)
                .padding(start = 5.dp, end = 5.dp)){
                HorizontalDashedLine()

            }
            Box(
                Modifier.wrapContentWidth(), contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .height(30.dp)
                        .width(15.dp)
//            .clipToBounds()
                ) {
                    drawArc(
                        color = CustomColors.lightBlue,
                        -90f,
                        180f,
                        useCenter = false,
                        topLeft = Offset(-15.dp.toPx(), 0.dp.toPx()),
                        size = Size(30.dp.toPx(), 30.dp.toPx()),
                    )
                }
            }
        }    }*/
}