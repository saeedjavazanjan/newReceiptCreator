package com.saeed.zanjan.receipt.presentation.ui.receipt

import HorizontalDashedLine
import android.annotation.SuppressLint
import android.app.Activity
import android.view.RoundedCorner
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import okhttp3.internal.wait

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReceiptScreen(
    navType:String?,
    receiptId:Int?,
    receiptCategory:Int?,
    navController: NavController
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
            BottomBar()
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
fun BottomBar(){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier=Modifier.padding(20.dp),
            elevation=CardDefaults.cardElevation(20.dp),
            shape= CircleShape,
            colors=CardDefaults.cardColors(
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
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.signeture),
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
@Composable
fun TopBar(
    onBackClicked:()->Unit
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
                painter = painterResource(id = R.drawable.delivered_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(CustomColors.lightGreen, shape = CircleShape)
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

