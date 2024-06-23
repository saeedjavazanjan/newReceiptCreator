package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@Composable
fun TextShowDialog(
    onDismiss:()->Unit,
    text:String
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = text,
                    style = MaterialTheme.typography.headlineLarge,
                    color = CustomColors.darkPurple
                )

                Spacer(modifier = Modifier.size(10.dp))
                TextButton(
                    onClick = {
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.lightBlue
                    )

                ) {
                    Text(text = "بستن",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }

            }

        }
    }

}