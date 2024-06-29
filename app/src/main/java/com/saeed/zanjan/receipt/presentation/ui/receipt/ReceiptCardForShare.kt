package com.saeed.zanjan.receipt.presentation.ui.receipt

import HorizontalDashedLine
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.domain.models.GeneralReceipt
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ReceiptCardForShare(
    modifier: Modifier,
    receiptCategory: Int?,
    companyName:String,
    avatar:String,
    companyPhone:String,
    rules:String,
    generalReceipt: GeneralReceipt
) {
    Card(
        modifier,
        border = BorderStroke(3.dp, CustomColors.lightBlue),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = CustomColors.lightBlue,
        )

    ) {
        Column {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(start = 30.dp, end = 30.dp)

                ) {
                    GlideImage(
                        model = avatar,
                        loading = placeholder(R.drawable.receipt_app_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.size(15.dp))

                    Text(
                        modifier = Modifier
                            .padding(1.dp)
                            .align(Alignment.CenterVertically),
                        text = companyName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.bitterDarkPurple
                    )
                }





                Row(
                    modifier = Modifier.padding(start = 30.dp, end = 30.dp)
                ) {
                    Column {

                        Text(
                            modifier = Modifier
                                .padding(1.dp)
                                .align(Alignment.CenterHorizontally),
                            text = "تاریخ دریافت",
                            style = MaterialTheme.typography.bodySmall,
                            color = CustomColors.bitterDarkPurple
                        )

                        generalReceipt.receiptTime?.let {
                            Text(
                                modifier = Modifier
                                    .padding(1.dp)
                                    .align(Alignment.CenterHorizontally),
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = CustomColors.darkPurple
                            )
                        }

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column {
                        Text(
                            modifier = Modifier
                                .padding(1.dp)
                                .align(Alignment.CenterHorizontally),
                            text = "موعد تحویل",
                            style = MaterialTheme.typography.bodySmall,
                            color = CustomColors.bitterDarkPurple
                        )
                        generalReceipt.deliveryTime?.let {
                            Text(
                                modifier = Modifier
                                    .padding(1.dp)
                                    .align(Alignment.CenterHorizontally),
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = CustomColors.darkPurple
                            )
                        }
                    }
                }
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

                    Box(
                        modifier =
                        Modifier
                            .weight(1f)
                            .height(2.dp)
                            .padding(start = 5.dp, end = 5.dp)
                    ) {
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
                Row {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    ) {

                        Text(
                            modifier = Modifier
                                .padding(1.dp)
                                .align(Alignment.Start),
                            text = "نام مشتری",
                            style = MaterialTheme.typography.bodySmall,
                            color = CustomColors.bitterDarkPurple
                        )
                        Text(
                            modifier = Modifier
                                .padding(1.dp)
                                .align(Alignment.Start),
                            text = generalReceipt.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = CustomColors.darkPurple
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(1.dp)
                                .align(Alignment.Start),
                            text = "نام کالا",
                            style = MaterialTheme.typography.bodySmall,
                            color = CustomColors.bitterDarkPurple
                        )
                        generalReceipt.orderName?.let {
                            Text(
                                modifier = Modifier
                                    .padding(1.dp)
                                    .align(Alignment.Start),
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = CustomColors.darkPurple
                            )
                        }
                    }
                }



                when (receiptCategory) {
                    0 -> {
                        RepairItems(generalReceipt)
                    }

                    1 -> {
                        RepairItems(generalReceipt)

                    }

                    2 -> {
                        RepairItems(generalReceipt)

                    }

                    3 -> {
                        TailoringItems(generalReceipt)

                    }

                    4 -> {
                        JewelryItems(generalReceipt)
                    }

                    5 -> {
                        PhotographyItems(generalReceipt)
                    }

                    6 -> {
                        LaundryItems(generalReceipt)
                    }

                    7 -> {
                        ConfectioneryItems(generalReceipt)

                    }

                    8 -> {
                        OtherJobsItems(generalReceipt)

                    }

                }
                Row {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(1.dp)
                                .align(Alignment.Start),
                            text = "مبلغ کل",
                            style = MaterialTheme.typography.bodySmall,
                            color = CustomColors.bitterDarkPurple
                        )
                        generalReceipt.cost?.let {
                            Text(
                                modifier = Modifier
                                    .padding(1.dp)
                                    .align(Alignment.Start),
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = CustomColors.darkPurple
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(1.dp)
                                .align(Alignment.Start),
                            text = "مبلغ پرداختی",
                            style = MaterialTheme.typography.bodySmall,
                            color = CustomColors.bitterDarkPurple
                        )
                        generalReceipt.prepayment?.let {
                            Text(
                                modifier = Modifier
                                    .padding(1.dp)
                                    .align(Alignment.Start),
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = CustomColors.darkPurple
                            )
                        }


                    }
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(1.dp)
                            .align(Alignment.Start),
                        text = "قوانین",
                        style = MaterialTheme.typography.bodySmall,
                        color = CustomColors.bitterDarkPurple
                    )
                    Text(
                        modifier = Modifier
                            .padding(1.dp)
                            .fillMaxWidth()
                            .align(Alignment.Start),
                        text = rules,
                        style = MaterialTheme.typography.bodySmall,
                        color = CustomColors.darkPurple
                    )

                }
                Spacer(modifier = Modifier.weight(1f))

                Divider(
                    color = CustomColors.lightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 5.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .padding(1.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "شماره تماس: $companyPhone",
                        style = MaterialTheme.typography.bodySmall,
                        color = CustomColors.bitterDarkPurple
                    )


                }
            }


        }


    }
}

@Composable
fun RepairItems(
    generalReceipt: GeneralReceipt
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "مشکل محصول ",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.repairLoanerProblems?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "خطرات احتمالی و توضیحات",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.repairRisks?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "لوازم همراه ",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.repairAccessories?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
    }


}

@Composable
fun TailoringItems(
    generalReceipt: GeneralReceipt
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "مشخصات ",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.tailoringOrderSpecification?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "اندازه ها",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.tailoringSizes?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
    }

}

@Composable
fun JewelryItems(
    generalReceipt: GeneralReceipt

) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "مشکل محصول ",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.jewelryLoanerProblems?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "توضیحات سفارش ساخت",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.jewelryOrderSpecification?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "مشخصات محصول تعمیری",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.jewelryLoanerSpecification?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
    }
}

@Composable
fun PhotographyItems(
    generalReceipt: GeneralReceipt

) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "تعداد سفارش",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.photographyOrderNumber?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "توضیحات و اندازه ها ",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.photographyOrderSize?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
    }
}

@Composable
fun LaundryItems(
    generalReceipt: GeneralReceipt

) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "نوع سفارش",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.laundryOrderType?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "توضیحات سفارش",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.laundryDescription?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
    }
}

@Composable
fun ConfectioneryItems(
    generalReceipt: GeneralReceipt

) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "توضیحات سفارش",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.confectioneryDescription?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "مشخصات",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.confectioneryOrderSpecification?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "وزن",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.confectioneryOrderWeight?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .align(Alignment.Start),
                    text = "$it کیلو گرم",
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
    }
}

@Composable
fun OtherJobsItems(
    generalReceipt: GeneralReceipt

) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "تعداد سفارش",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.otherJobsOrderNumber?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "توضیحات سفارش",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.otherJobsDescription?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
        Column {
            Text(
                modifier = Modifier
                    .padding(1.dp)
                    .align(Alignment.Start),
                text = "وزن",
                style = MaterialTheme.typography.bodySmall,
                color = CustomColors.bitterDarkPurple
            )
            generalReceipt.confectioneryOrderWeight?.let {
                Text(
                    modifier = Modifier
                        .padding(1.dp)
                        .align(Alignment.Start),
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = CustomColors.darkPurple
                )
            }
        }
    }
}