package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
@Composable
fun ExitDialog(
    onDismiss: () -> Unit,
    onExitClicked: () -> Unit

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
                text = "هشدار",
                style = MaterialTheme.typography.titleLarge,
                color = CustomColors.darkPurple
            )

            Text(
                textAlign = TextAlign.Start,
                text = "اطلاعات ذخیره نشده است آیا قصد بستن صفحه را دارید؟",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.darkPurple
            )

            Row{
                TextButton(
                    onClick = {
                         onExitClicked()
                    }

                ) {
                    Text(text = "خروج",
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