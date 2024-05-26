package com.saeed.zanjan.receipt.presentation.ui.receipt

import HorizontalDashedLine
import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.presentation.components.BottomBar
import com.saeed.zanjan.receipt.presentation.components.TopBar
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReceiptScreen(
    navType:String?,
    receiptId:Int?,
    receiptCategory:Int?,
    navController: NavController,
    onNavigateToEdit:(String) -> Unit
){

    NewReceiptCreatorTheme(
        displayProgressBar=false,
        themColor = CustomColors.lightBlue
    ) {

        Scaffold(
            topBar = {
                     TopBar(
                         onBackClicked = {
                             navController.popBackStack()
                         }
                     )
            }  ,
            bottomBar = {
            BottomBar(
                itemClicked = {
                    onNavigateToEdit(it)
                }
            )
            }
        ) {

            Column(
                modifier = Modifier
                    .background(CustomColors.lightBlue)
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, top = it.calculateTopPadding())
            ) {


                ReceiptCard(modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth()
                    .weight(1f),
                    receiptCategory = receiptCategory
                )


            }


        }


    }

}



@Composable
fun ReceiptCard(modifier: Modifier,receiptCategory:Int?){
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
                painter = painterResource(id = R.drawable.ready_to_delivery_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(CustomColors.readyForDelivery, shape = CircleShape)
                    .padding(10.dp)
                    .size(30.dp)
            )
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally),
                text = "تحویل شده",
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
                        Text(
                            modifier = Modifier
                                .padding(3.dp)
                                .align(Alignment.CenterHorizontally),
                            text = "1403/1/25",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CustomColors.darkPurple
                        )

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
                        Text(
                            modifier = Modifier
                                .padding(3.dp)
                                .align(Alignment.CenterHorizontally),
                            text = "1403/2/10",
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
                        text = "سعید غفاری",
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
                    Text(
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.Start),
                        text = "09193480263",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )
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
                    Text(
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.Start),
                        text = "لپ تاپ لنوو",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )
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
                        RepairItems()
                    }
                    1->{
                        RepairItems()

                    }
                    2->{
                        RepairItems()

                    }
                    3->{
                        TailoringItems()

                    }
                    4->{
                        JewelryItems()
                    }
                    5->{
                        PhotographyItems()
                    }
                    6->{
                        LaundryItems()
                    }
                    7->{
                        ConfectioneryItems()

                    }
                    8->{
                        OtherJobsItems()

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
                    Text(
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.Start),
                        text = "250000 تومان",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CustomColors.darkPurple
                    )
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
                    Text(
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.Start),
                        text = "50000 تومان",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CustomColors.darkPurple
                    )
                    Spacer(modifier = Modifier.size(90.dp))
                }
            }



        }


    }
}
@Composable
fun RepairItems(){
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
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxWidth()
                    .align(Alignment.Start),
                text = "خرابی تاچ پد ال سیدی وقتی شات دان میکنیم خاموش نمیشه",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.darkPurple
            )
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
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "احتمال سوختن برد وجود دارد \n همچنین ممکن است لپتاپ بسوزد",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.darkPurple
            )
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
            Text(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.Start),
                text = "کیف موس و آداپتور",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.darkPurple
            )
        }
    }


}
@Composable
fun TailoringItems(){

}
@Composable
fun JewelryItems(){

}
@Composable
fun PhotographyItems(){

}
@Composable
fun LaundryItems(){

}
@Composable
fun ConfectioneryItems(){

}
@Composable
fun OtherJobsItems(){

}

