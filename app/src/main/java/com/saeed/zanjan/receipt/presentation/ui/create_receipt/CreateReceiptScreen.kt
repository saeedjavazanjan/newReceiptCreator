package com.saeed.zanjan.receipt.presentation.ui.create_receipt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saeed.zanjan.receipt.presentation.components.TopBar
import com.saeed.zanjan.receipt.presentation.ui.receipt.BottomBar
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.presentation.ui.receipt.ConfectioneryItems
import com.saeed.zanjan.receipt.presentation.ui.receipt.JewelryItems
import com.saeed.zanjan.receipt.presentation.ui.receipt.LaundryItems
import com.saeed.zanjan.receipt.presentation.ui.receipt.OtherJobsItems
import com.saeed.zanjan.receipt.presentation.ui.receipt.PhotographyItems
import com.saeed.zanjan.receipt.presentation.ui.receipt.RepairItems
import com.saeed.zanjan.receipt.presentation.ui.receipt.TailoringItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReceiptScreen(
    navController:NavController
) {
    var receiveDate by remember {
        mutableStateOf("")
    }
 val deliveryDate by remember {
        mutableStateOf("")
    }

val receiptCategory=1

    NewReceiptCreatorTheme(
        displayProgressBar=false,
        themColor = CustomColors.lightBlue
    ) {

        Scaffold(
            containerColor = CustomColors.lightBlue,
            topBar = {
                TopBar(
                    onBackClicked = {
                        navController.popBackStack()
                    }
                )
            }  ,
            bottomBar = {
                CrateReceiptBottomBar()
            }
        ) {

            Card(
                modifier = Modifier
                    .padding(
                        start = 25.dp,
                        end = 25.dp,
                        top = it.calculateTopPadding(),
                        bottom = 16.dp
                    )
                    .fillMaxSize()
                ,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = CustomColors.lightBlue,
                )

            ) {
                Column (
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ){
                    Row(
                        modifier = Modifier
                            .padding()
                            .fillMaxWidth()
                        ,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column{
                            Text(
                                modifier = Modifier,
                                text = "تاریخ دریافت",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .width(150.dp)
                                    .clickable {

                                    },
                                shape = RoundedCornerShape(30.dp),
                                readOnly=true,
                                value ="receiveDate" ,
                                onValueChange ={receive->
                                    receiveDate=receive
                                },
                                leadingIcon={
                                    Icon( painter = painterResource(id = R.drawable.calendar),
                                        tint = CustomColors.gray,
                                        contentDescription = null)
                                },
                                colors =TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = CustomColors.darkPurple,
                                    containerColor = CustomColors.transparentLightGray,
                                    cursorColor = CustomColors.lightBlue,
                                    focusedBorderColor = CustomColors.lightBlue,
                                    unfocusedBorderColor = CustomColors.lightGray
                                )
                            )

                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Column {
                            Text(
                                modifier = Modifier
                                ,
                                text = "موعد تحویل",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.bitterDarkPurple
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .width(150.dp)
                                    .clickable {

                                    },
                                shape = RoundedCornerShape(30.dp),
                                readOnly=true,
                                value ="receiveDate" ,
                                onValueChange ={receive->
                                    receiveDate=receive
                                },
                                leadingIcon={
                                    Icon( painter = painterResource(id = R.drawable.calendar_checked),
                                        tint = CustomColors.gray,
                                        contentDescription = null)
                                },
                                colors =TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = CustomColors.darkPurple,
                                    containerColor = CustomColors.transparentLightGray,
                                    cursorColor = CustomColors.lightBlue,
                                    focusedBorderColor = CustomColors.lightBlue,
                                    unfocusedBorderColor = CustomColors.lightGray
                                )
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
                    Column{
                        Text(
                            modifier = Modifier,
                            text = "نام مشتری",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CustomColors.bitterDarkPurple
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                },
                            value ="" ,
                            shape = RoundedCornerShape(30.dp),
                            onValueChange ={receive->
                                receiveDate=receive
                            },
                            leadingIcon={
                                Icon( painter = painterResource(id = R.drawable.user),
                                    tint = CustomColors.gray,
                                    contentDescription = null)
                            },
                            colors =TextFieldDefaults.outlinedTextFieldColors(
                                textColor = CustomColors.darkPurple,
                                containerColor = CustomColors.transparentLightGray,
                                cursorColor = CustomColors.lightBlue,
                                focusedBorderColor = CustomColors.lightBlue,
                                unfocusedBorderColor = CustomColors.lightGray
                            )
                        )

                    }
                    Column{
                        Text(
                            modifier = Modifier,
                            text = "شماره تلفن",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CustomColors.bitterDarkPurple
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                },
                            shape = RoundedCornerShape(30.dp),

                            value ="" ,
                            onValueChange ={receive->
                                receiveDate=receive
                            },
                            leadingIcon={
                                Icon( painter = painterResource(id = R.drawable.call),
                                    tint = CustomColors.gray,
                                    contentDescription = null)
                            },
                            colors =TextFieldDefaults.outlinedTextFieldColors(
                                textColor = CustomColors.darkPurple,
                                containerColor = CustomColors.transparentLightGray,
                                cursorColor = CustomColors.lightBlue,
                                focusedBorderColor = CustomColors.lightBlue,
                                unfocusedBorderColor = CustomColors.lightGray
                            )
                        )

                    }
                    Column{
                        Text(
                            modifier = Modifier,
                            text = "نام کالا",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CustomColors.bitterDarkPurple
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                },
                            value ="" ,
                            shape = RoundedCornerShape(30.dp),
                            onValueChange ={receive->
                                receiveDate=receive
                            },
                            leadingIcon={
                                Icon( painter = painterResource(id = R.drawable.cart_3),
                                    tint = CustomColors.gray,
                                    contentDescription = null)
                            },
                            colors =TextFieldDefaults.outlinedTextFieldColors(
                                textColor = CustomColors.darkPurple,
                                containerColor = CustomColors.transparentLightGray,
                                cursorColor = CustomColors.lightBlue,
                                focusedBorderColor = CustomColors.lightBlue,
                                unfocusedBorderColor = CustomColors.lightGray
                            )
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
                            RepairFields()
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
                    Column{
                        Text(
                            modifier = Modifier,
                            text = "مبلغ کل اعلامی",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CustomColors.bitterDarkPurple
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                },
                            value ="" ,
                            shape = RoundedCornerShape(30.dp),
                            onValueChange ={receive->
                                receiveDate=receive
                            },
                            leadingIcon={
                                Icon( painter = painterResource(id = R.drawable.credit_card),
                                    tint = CustomColors.gray,
                                    contentDescription = null)
                            },
                            colors =TextFieldDefaults.outlinedTextFieldColors(
                                textColor = CustomColors.darkPurple,
                                containerColor = CustomColors.transparentLightGray,
                                cursorColor = CustomColors.lightBlue,
                                focusedBorderColor = CustomColors.lightBlue,
                                unfocusedBorderColor = CustomColors.lightGray
                            )
                        )

                    }
                    Column{
                        Text(
                            modifier = Modifier,
                            text = "مبلغ پرداخت شده",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CustomColors.bitterDarkPurple
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                },
                            value ="" ,
                            shape = RoundedCornerShape(30.dp),
                            onValueChange ={receive->
                                receiveDate=receive
                            },
                            leadingIcon={
                                Icon( painter = painterResource(id = R.drawable.paied_card),
                                    tint = CustomColors.gray,
                                    contentDescription = null)
                            },
                            colors =TextFieldDefaults.outlinedTextFieldColors(
                                textColor = CustomColors.darkPurple,
                                containerColor = CustomColors.transparentLightGray,
                                cursorColor = CustomColors.lightBlue,
                                focusedBorderColor = CustomColors.lightBlue,
                                unfocusedBorderColor = CustomColors.lightGray
                            )
                        )

                    }
                    Spacer(modifier = Modifier.size(90.dp))

                }
            }

            }
        }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepairFields(){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        Column{
            Text(
                modifier = Modifier,
                text = "مشکل کالا",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    },
                value ="" ,
                shape = RoundedCornerShape(30.dp),
                onValueChange ={receive->
                },
                leadingIcon={
                    Icon( painter = painterResource(id = R.drawable.settings),
                        tint = CustomColors.gray,
                        contentDescription = null)
                },
                colors =TextFieldDefaults.outlinedTextFieldColors(
                    textColor = CustomColors.darkPurple,
                    containerColor = CustomColors.transparentLightGray,
                    cursorColor = CustomColors.lightBlue,
                    focusedBorderColor = CustomColors.lightBlue,
                    unfocusedBorderColor = CustomColors.lightGray
                )
            )

        }
        Column{
            Text(
                modifier = Modifier,
                text = "خطرات احتمالی و توضیحات",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    },
                value ="bdhh dgrgrg dfvgrgrg dgvrg\nsdvsdvsfdvfdv\nvsdvfdvbfdvb\n" ,
                shape = RoundedCornerShape(30.dp),
                onValueChange ={receive->
                },
                leadingIcon={
                    Icon( painter = painterResource(id = R.drawable.alarm),
                        tint = CustomColors.gray,
                        contentDescription = null)
                },
                colors =TextFieldDefaults.outlinedTextFieldColors(
                    textColor = CustomColors.darkPurple,
                    containerColor = CustomColors.transparentLightGray,
                    cursorColor = CustomColors.lightBlue,
                    focusedBorderColor = CustomColors.lightBlue,
                    unfocusedBorderColor = CustomColors.lightGray
                )
            )

        }
        Column{
            Text(
                modifier = Modifier,
                text = "لوازم همراه",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    },
                value ="" ,
                shape = RoundedCornerShape(30.dp),
                onValueChange ={receive->
                },
                leadingIcon={
                    Icon( painter = painterResource(id = R.drawable.cart_1),
                        tint = CustomColors.gray,
                        contentDescription = null)
                },
                colors =TextFieldDefaults.outlinedTextFieldColors(
                    textColor = CustomColors.darkPurple,
                    containerColor = CustomColors.transparentLightGray,
                    cursorColor = CustomColors.lightBlue,
                    focusedBorderColor = CustomColors.lightBlue,
                    unfocusedBorderColor = CustomColors.lightGray
                )
            )

        }


    }
}
@Composable
fun CrateReceiptBottomBar(){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier=Modifier.padding(30.dp),
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
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    Button(
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .padding(2.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomColors.lightGray
                        ),
                        onClick = {
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.checked),
                            tint = CustomColors.darkPurple,
                            contentDescription = null
                        )
                    }

                }


                Spacer(modifier = Modifier.size(30.dp))
                Column {
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
                            painter = painterResource(id = R.drawable.chart),
                            tint = CustomColors.darkPurple,
                            contentDescription = null
                        )
                    }

                }



            }
        }
    }
}