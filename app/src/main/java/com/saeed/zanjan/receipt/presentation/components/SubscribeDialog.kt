package com.saeed.zanjan.receipt.presentation.components

import android.content.Context
import android.view.Window
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme
import okhttp3.internal.wait

@Composable
fun SubscribeDialog(
    onDismiss: () -> Unit,
    buySubscribe: (String) -> Unit,
    leftTime:MutableState<Long>,
    context: Context,
) {


    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.widthIn(max = 500.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Card(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = CustomColors.lightBlue,
                    )

                ) {
                    Row {
                        Column(
                            verticalArrangement = Arrangement.Center,
                        ) {
                            TextButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = {
                                    if(leftTime.value<=0){
                                        buySubscribe("1month")
                                        onDismiss()
                                    }
                                    else
                                        Toast.makeText(context,"اشتراک فعلی شما به پایان نرسیده",Toast.LENGTH_SHORT).show()
                                }) {

                                Text(
                                    text = "خرید اشتراک 1 ماهه",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = CustomColors.darkPurple
                                )

                            }
                            Divider(
                                color = CustomColors.lightGray,
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(2.dp)
                                    .padding(horizontal = 5.dp)
                                    .align(Alignment.CenterHorizontally)

                            )
                            TextButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = {
                                    if(leftTime.value<=0){
                                        buySubscribe("3month")
                                        onDismiss()
                                    }
                                    else
                                        Toast.makeText(context,"اشتراک فعلی شما به پایان نرسیده",Toast.LENGTH_SHORT).show()

                                }) {

                                Text(
                                    text = "خرید اشتراک 3 ماهه",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = CustomColors.darkPurple
                                )

                            }
                            Divider(
                                color = CustomColors.lightGray,
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(2.dp)
                                    .padding(horizontal = 5.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            TextButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = {
                                    if(leftTime.value<=0){
                                        buySubscribe("6month")
                                        onDismiss()

                                    }
                                    else
                                        Toast.makeText(context,"اشتراک فعلی شما به پایان نرسیده",Toast.LENGTH_SHORT).show()
                                }) {

                                Text(
                                    text = "خرید اشتراک 6 ماهه",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = CustomColors.darkPurple
                                )

                            }

                        }

                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .background(CustomColors.lightBlue),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Spacer(modifier = Modifier.size(20.dp))

                            Text(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.CenterHorizontally),
                                text = leftTime.value.toString(),
                                style = MaterialTheme.typography.displayLarge,
                                color = CustomColors.yellow
                            )
                            Spacer(modifier = Modifier.size(10.dp))

                            Text(
                                modifier = Modifier.padding(5.dp),
                                text = "روز از اشتراک شماباقی مانده.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.size(40.dp))


                        }


                    }
                }
            }
        }
    }



}
