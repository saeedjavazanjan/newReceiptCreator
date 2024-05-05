package com.saeed.zanjan.receipt.presentation.ui.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    onSendOtpClicked: (String) -> Unit,
    onDismiss: () -> Unit,

) {
    var otp by remember { mutableStateOf("") }


        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false
            )
        ) {
            val snackbarHostState = remember { SnackbarHostState() }
            val coroutineScope = rememberCoroutineScope()
            var checkOtpClicked by remember { mutableStateOf(false) }

            Surface(
                modifier = Modifier.widthIn(max = 400.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "رمز یکبار مصرف",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    OutlinedTextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = otp,
                        onValueChange = { otp = it },
                        label = {

                            Text("رمز دریافتی را وارد کنید",
                            style = MaterialTheme.typography.bodyMedium)

                                },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        isError = (otp.isEmpty() || otp.length < 4) && checkOtpClicked,
                        textStyle = MaterialTheme.typography.bodySmall
                        )
                    if ((otp.isEmpty() || otp.length < 4) && checkOtpClicked) {
                        Text(
                            text = "لطفا رمز دریافتی را وارد کنید",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = onDismiss
                        ) {
                            Text("انصراف",
                                    style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Button(
                            onClick = {
                                checkOtpClicked = true
                                if (otp.isNotEmpty() || otp.length >= 4) {
                                    onSendOtpClicked(otp)

                                }
                            }
                        ) {
                            Text("ارسال",
                                    style = MaterialTheme.typography.bodyMedium

                                )
                        }
                    }
                }
            }
        }


}
