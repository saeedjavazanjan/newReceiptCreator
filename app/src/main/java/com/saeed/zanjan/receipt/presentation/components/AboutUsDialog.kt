package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
fun AboutUsDialog(
    onDismiss: () -> Unit,
    description: String,
    telegramLink: String,
    gmail: String,
    modifier: Modifier,
    buttonClicked: () -> Unit = { onDismiss() },
    intentToTelegram: (String) -> Unit,
    intentToGmail: (String) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 40.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = CustomColors.darkPurple
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    modifier = Modifier.clickable {
                        intentToTelegram(telegramLink)
                    },
                    text = telegramLink,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkBlue
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    modifier = Modifier.clickable {
                        intentToGmail(gmail)
                    },
                    text = gmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CustomColors.darkBlue
                )
                Spacer(modifier = Modifier.size(20.dp))

                TextButton(
                    onClick = {
                        buttonClicked()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.lightBlue
                    )

                ) {
                    Text(
                        text = "بستن",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
            }


        }
    }

}