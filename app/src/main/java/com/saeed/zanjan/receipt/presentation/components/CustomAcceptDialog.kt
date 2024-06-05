package com.saeed.zanjan.receipt.presentation.components

import android.service.autofill.CustomDescription
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@Composable
fun CustomAcceptDialog(
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
    title:String,
    description: String,
    acceptText:String

){

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
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = CustomColors.darkPurple
                )

                Text(
                    textAlign = TextAlign.Start,
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkPurple
                )

                Row{
                    TextButton(
                        onClick = {
                            onAccept()
                        }

                    ) {
                        Text(text = acceptText,
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