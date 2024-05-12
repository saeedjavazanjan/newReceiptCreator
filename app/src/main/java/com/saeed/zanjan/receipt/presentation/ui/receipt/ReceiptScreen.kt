package com.saeed.zanjan.receipt.presentation.ui.receipt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme

@Composable
fun ReceiptScreen(
    navType:String?,
    receiptId:Int?,
    receiptCategory:Int?
){

    NewReceiptCreatorTheme(
        displayProgressBar=false
    ) {
        Column(
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




        }

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