package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
@Composable
fun SubscribeDialog(
    onDismiss: () -> Unit={},
    ) {


    Dialog(onDismissRequest =  onDismiss ) {
        Surface(
            modifier = Modifier.widthIn(max = 500.dp),
            shape = RoundedCornerShape(8.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Card(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = CustomColors.lightBlue,
                    )

                ) {
                    Row{
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(10.dp),
                        ) {
                            TextButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = { /*TODO*/ }) {

                                Text(
                                    text = "خرید اشتراک 1 ماهه",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = CustomColors.darkPurple
                                )

                            }
                            TextButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = { /*TODO*/ }) {

                                Text(
                                    text = "خرید اشتراک 3 ماهه",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = CustomColors.darkPurple
                                )

                            }
                            TextButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = { /*TODO*/ }) {

                                Text(
                                    text = "خرید اشتراک 1 ساله",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = CustomColors.darkPurple
                                )

                            }

                        }

                        Column(
                            modifier= Modifier
                                .padding(10.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "از اشتراک شما ",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.darkPurple
                            )
                            Spacer(modifier = Modifier.size(20.dp))
                            Text(
                                text = "195",
                                style = MaterialTheme.typography.displayLarge,
                                color = CustomColors.readyForDelivery
                            )
                            Spacer(modifier = Modifier.size(20.dp))

                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "روز باقی مانده.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CustomColors.darkPurple
                            )


                        }


                    }
                }
            }
        }
    }


}