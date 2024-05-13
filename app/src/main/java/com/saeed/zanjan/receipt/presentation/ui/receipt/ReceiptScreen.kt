package com.saeed.zanjan.receipt.presentation.ui.receipt

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import okhttp3.internal.wait

@Composable
fun ReceiptScreen(
    navType:String?,
    receiptId:Int?,
    receiptCategory:Int?
){
    val view = LocalView.current
    if (!view.isInEditMode) {

    }
    NewReceiptCreatorTheme(
        displayProgressBar=false,
        themColor = CustomColors.lightBlue
    ) {


        Column(
            modifier = Modifier
                .background(CustomColors.lightBlue)
                .fillMaxSize()

        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp)
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
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.right_arrow_2),
                        tint = CustomColors.darkPurple,
                        contentDescription = null
                    )
                }
            }



            Card(
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 25.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
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
                        painter = painterResource(id = R.drawable.paied_card),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .background(Color.Black, shape = CircleShape)
                            .padding(10.dp)
                            .size(50.dp)
                    )

                    Spacer(modifier = Modifier.size(50.dp))
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
                            .padding(start = 5.dp, end = 5.dp)
                            .background(CustomColors.lightGray))
                        Box(
                            modifier = Modifier.wrapContentWidth(),
                            contentAlignment = Alignment.CenterStart // Align to the start (left) of the parent
                        ) {
                            Canvas(
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(15.dp)
                                    .padding(end = 0.dp) // Ensure no padding on the start side

                            ) {
                                drawArc(
                                    color = CustomColors.lightBlue,
                                    startAngle = -90f,
                                    sweepAngle = 180f,
                                    useCenter = false,
                                    topLeft = Offset(
                                        -42f,
                                        0f
                                    ), // Start from the left edge of the Canvas
                                    size = Size(30.dp.toPx(), 30.dp.toPx())
                                )
                            }
                            //    }
                        }
                    }
                }


            }
        }

      /*  Column(
            modifier = Modifier.padding(10.dp)
        ){

            Row(
                modifier = Modifier.fillMaxWidth()
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
                    },
                ) {
                    Icon(
                        painter = painterResource(id= R.drawable.right_arrow_2),
                        tint = CustomColors.darkPurple,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.size(30.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "تاریخ دریافت",
                        style = MaterialTheme.typography.bodySmall,
                        color = CustomColors.darkPurple
                    )
                   Row (
                       verticalAlignment = Alignment.CenterVertically

                   ){
                       Icon(painter = painterResource(id = R.drawable.calendar),
                           tint = Color.Gray
                           , contentDescription =null )
                       Spacer(modifier = Modifier.size(5.dp))
                       Text(
                           text = "20.1.1",
                           style = MaterialTheme.typography.bodyLarge,
                           color = CustomColors.bitterDarkPurple
                       )
                   }
                }
                Spacer(modifier = Modifier.size(15.dp))
                Column(
                    modifier = Modifier.weight(1f)

                ) {
                    Text(
                        text = "موعد تحویل",
                        style = MaterialTheme.typography.bodySmall,
                        color = CustomColors.darkPurple
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painter = painterResource(id = R.drawable.calendar_checked),
                            tint = Color.Gray
                            , contentDescription =null )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            text = "20.1.1",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CustomColors.bitterDarkPurple
                        )
                    }
                }
            }

            Column (
                modifier = Modifier
                    .weight(1f)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)

            ){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.user),
                        tint = Color.Gray
                        , contentDescription =null )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "نام مشتری",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = " میللیببیذ بیذلیاذ فیاذاسعید غفاری",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CustomColors.bitterDarkPurple
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.call),
                        tint = Color.Gray
                        , contentDescription =null )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "تلفن",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "0919***0263",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CustomColors.bitterDarkPurple
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.cart_3),
                        tint = Color.Gray
                        , contentDescription =null )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "نام کالا",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "Xiaomi note10",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CustomColors.bitterDarkPurple
                    )
                }

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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.credit_card),
                        tint = Color.Gray
                        , contentDescription =null )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "هزینه اعلامی",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "10000"+"تومان",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CustomColors.bitterDarkPurple
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.paied_card),
                        tint = Color.Gray
                        , contentDescription =null )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = "هزینه پرداخت شده",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "5000"+"تومان",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CustomColors.bitterDarkPurple
                    )
                }


            }




        }*/

    }



}
@Composable
fun RepairItems(){
    Column{
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.settings),
                tint = Color.Gray
                , contentDescription =null )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "مشکل محصول",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.darkPurple
            )

        }
        Spacer(modifier = Modifier.size(5.dp))
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Xiaomi note10 Xiaomi note10  Xiaomi note10  " +
                    "Xiaomi note10  Xiaomi note10  Xiaomi note10 ",
            style = MaterialTheme.typography.bodyLarge,
            color = CustomColors.bitterDarkPurple
        )
        Spacer(modifier = Modifier.size(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.alarm),
                tint = Color.Gray
                , contentDescription =null )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "خطرات احتمالی",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.darkPurple
            )

        }
        Spacer(modifier = Modifier.size(5.dp))
        Text(
            modifier = Modifier.padding(10.dp),
            text = "خطرات احتمالی خطرات احتمالی خطرات احتمالی خطرات" +
                    " احتمالی خطرات احتمالی خطرات احتمالی ",
            style = MaterialTheme.typography.bodyLarge,
            color = CustomColors.bitterDarkPurple
        )

        Spacer(modifier = Modifier.size(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.cart_1),
                tint = Color.Gray
                , contentDescription =null )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "لوازم همراه کالا",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.darkPurple
            )

        }
        Spacer(modifier = Modifier.size(5.dp))
        Text(
            modifier = Modifier.padding(10.dp),
            text = "خطرات احتمالی خطرات احتمالی خطرات احتمالی خطرات" +
                    " احتمالی خطرات احتمالی خطرات احتمالی ",
            style = MaterialTheme.typography.bodyLarge,
            color = CustomColors.bitterDarkPurple
        )
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

