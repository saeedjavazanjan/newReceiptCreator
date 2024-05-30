package com.saeed.zanjan.receipt.presentation.components

import HorizontalDashedLine
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@Composable
fun ReceiptCard(
    modifier: Modifier,
    receiptCategory:Int?,
    generalReceipt: GeneralReceipt
){
    Card(
        modifier
        ,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = CustomColors.lightBlue,
        )

    ) {
        Column{
            Spacer(modifier = Modifier.size(15.dp))

            Image(
                painter =when(generalReceipt.status){
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

                } ,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(
                        when(generalReceipt.status){
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

                        },

                        shape = CircleShape)
                    .padding(10.dp)
                    .size(30.dp)
            )
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally),
                text =when(generalReceipt.status){
                       0->{
                           "در حال انجام"
                       }
                    1->{
                           "تحویل داده شده"
                       }
                    2->{
                           "مشکل در روند سفارش"
                       }
                    3->{
                          "آماده تحویل"
                       }
                    else->{
                        "در حال انجام"
                    }

              }


                ,
                style = MaterialTheme.typography.titleLarge,
                color = CustomColors.bitterDarkPurple
            )

            Spacer(modifier = Modifier.size(30.dp))
            Row(
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
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(start=30.dp,end=30.dp)
                ) {
                    Column {

                            Text(
                                modifier = Modifier
                                    .padding(3.dp)
                                    .align(Alignment.CenterHorizontally),
                                text = "تاریخ دریافت",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )

                        generalReceipt.receiptTime?.let {
                            Text(
                                modifier = Modifier
                                    .padding(3.dp)
                                    .align(Alignment.CenterHorizontally),
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.darkPurple
                            )
                        }

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column {
                        Text(
                            modifier = Modifier
                                .padding(3.dp)
                                .align(Alignment.CenterHorizontally),
                            text = "موعد تحویل",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CustomColors.bitterDarkPurple
                        )
                        generalReceipt.deliveryTime?.let {
                            Text(
                                modifier = Modifier
                                    .padding(3.dp)
                                    .align(Alignment.CenterHorizontally),
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.darkPurple
                            )
                        }
                    }
                }
                Divider(
                    color = CustomColors.lightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .padding(horizontal = 5.dp)
                )

                Column {
                    Text(
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.Start),
                        text = "نام مشتری",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.bitterDarkPurple
                    )
                    Text(
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.Start),
                        text = generalReceipt.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )
                }
                Column {
                    Text(
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.Start),
                        text = "شماره تماس",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.bitterDarkPurple
                    )
                    generalReceipt.phone?.let {
                        Text(
                            modifier = Modifier
                                .padding(3.dp)
                                .align(Alignment.Start),
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = CustomColors.darkPurple
                        )
                    }
                }
                Column {
                    Text(
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.Start),
                        text = "نام کالا",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.bitterDarkPurple
                    )
                    generalReceipt.orderName?.let {
                        Text(
                            modifier = Modifier
                                .padding(3.dp)
                                .align(Alignment.Start),
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = CustomColors.darkPurple
                        )
                    }
                }
                Divider(
                    color = CustomColors.lightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .padding(horizontal = 5.dp)
                )
                when(receiptCategory){
                    0->{
                        RepairItems(generalReceipt)
                    }
                    1->{
                        RepairItems(generalReceipt)

                    }
                    2->{
                        RepairItems(generalReceipt)

                    }
                    3->{
                        TailoringItems(generalReceipt)

                    }
                    4->{
                        JewelryItems(generalReceipt)
                    }
                    5->{
                        PhotographyItems(generalReceipt)
                    }
                    6->{
                        LaundryItems(generalReceipt)
                    }
                    7->{
                        ConfectioneryItems(generalReceipt)

                    }
                    8->{
                        OtherJobsItems(generalReceipt)

                    }

                }
                Divider(
                    color = CustomColors.lightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .padding(horizontal = 5.dp)
                )
                Column {
                    Text(
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.Start),
                        text = "مبلغ کل",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.bitterDarkPurple
                    )
                    generalReceipt.cost?.let {
                        Text(
                            modifier = Modifier
                                .padding(3.dp)
                                .align(Alignment.Start),
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            color = CustomColors.darkPurple
                        )
                    }
                }
                Column {
                    Text(
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.Start),
                        text = "مبلغ پرداختی",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.bitterDarkPurple
                    )
                    generalReceipt.prepayment?.let {
                        Text(
                            modifier = Modifier
                                .padding(3.dp)
                                .align(Alignment.Start),
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            color = CustomColors.darkPurple
                        )
                    }
                    Spacer(modifier = Modifier.size(90.dp))
                }
            }



        }


    }
}

@Composable
fun RepairItems(
    generalReceipt: GeneralReceipt
){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "مشکل محصول ",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.repairLoanerProblems?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "خطرات احتمالی و توضیحات",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.repairRisks?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "لوازم همراه ",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.repairAccessories?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
    }


}
@Composable
fun TailoringItems(
    generalReceipt: GeneralReceipt
){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "مشخصات ",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.tailoringOrderSpecification?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "اندازه ها",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.tailoringSizes?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
    }

}
@Composable
fun JewelryItems(
    generalReceipt: GeneralReceipt

){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "مشکل محصول ",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.jewelryLoanerProblems?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "توضیحات سفارش ساخت",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.jewelryOrderSpecification?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "مشخصات محصول تعمیری",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.jewelryLoanerSpecification?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
    }
}
@Composable
fun PhotographyItems(
    generalReceipt: GeneralReceipt

){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "تعداد سفارش",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.photographyOrderNumber?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "توضیحات و اندازه ها ",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.photographyOrderSize?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
    }
}
@Composable
fun LaundryItems(
    generalReceipt: GeneralReceipt

){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "نوع سفارش",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.laundryOrderType?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "توضیحات سفارش",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.laundryDescription?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
    }
}
@Composable
fun ConfectioneryItems(
    generalReceipt: GeneralReceipt

){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "توضیحات سفارش",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.confectioneryDescription?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "مشخصات",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.confectioneryOrderSpecification?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "وزن",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.confectioneryOrderWeight?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
    }
}
@Composable
fun OtherJobsItems(
    generalReceipt: GeneralReceipt

){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "تعداد سفارش",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.otherJobsOrderNumber?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "توضیحات سفارش",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.otherJobsDescription?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "وزن",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.confectioneryOrderWeight?.let {
                Text(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )
            }
        }
    }
}