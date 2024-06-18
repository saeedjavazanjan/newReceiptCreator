package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSMSTextDialog(
    onDismiss:()->Unit,
    onAccept:(String)->Unit

){

    var SMSText by remember { mutableStateOf("") }

    Dialog(onDismissRequest =  onDismiss ) {
        Surface(
            modifier = Modifier.widthIn(max = 400.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clickable {

                        },
                    singleLine = false,
                    label ={
                           Text(text = "متن پیامک")
                    },
                    maxLines = 5,
                    value = SMSText,
                    shape = RoundedCornerShape(30.dp),
                    onValueChange = { text ->
                        SMSText=text
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = CustomColors.darkPurple,
                        containerColor = CustomColors.transparentLightGray,
                        cursorColor = CustomColors.lightBlue,
                        focusedBorderColor = CustomColors.lightBlue,
                        unfocusedBorderColor = CustomColors.lightGray
                    )
                )

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ){
                    TextButton(
                        onClick = {
                            onAccept(SMSText)
                        }

                    ) {
                        Text(text = "ارسال",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CustomColors.darkPurple
                        )
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                    TextButton(
                        onClick = {
                            onDismiss()
                        }

                    ) {
                        Text(text = "انصراف",
                            style = MaterialTheme.typography.bodyLarge,
                            color = CustomColors.darkPurple
                        )
                    }
                }
            }




        }
    }




}