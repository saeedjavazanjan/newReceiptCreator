package com.saeed.zanjan.receipt.presentation.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home() {

    var isSearchExpanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(isSearchExpanded) {
        if (isSearchExpanded) {
            focusRequester.requestFocus()
        }
    }
    NewReceiptCreatorTheme(
        displayProgressBar=false
    ) {
    Scaffold(
        /* topBar = {
             TopAppBar(
                 title = { Text("My App") },
                 navigationIcon = {

                 },
                 actions = {
                     IconButton(
                         onClick = {
                         //    Handle filter action
                         }
                     ) {
                         Icon(Icons.Default.Star, contentDescription = null)
                     }
                 }
             )
         }*/
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {


            Row(modifier = Modifier.padding(vertical = 15.dp)) {
                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.lightGray
                    ),
                    onClick = {
                        //    Handle navigation
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.menu),
                        tint = CustomColors.darkPurple,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.lightGray
                    ),
                    onClick = {
                        //    Handle navigation
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        tint = CustomColors.darkPurple,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Card(
                    shape = RoundedCornerShape(30.dp),
                    colors = CardDefaults.cardColors(
                        contentColor = CustomColors.lightGray,
                        containerColor = CustomColors.lightGray
                    )
                ) {
                    Row {
                        AnimatedVisibility(
                            visible = !isSearchExpanded,
                            enter = fadeIn(),
                            exit = fadeOut()
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
                                    isSearchExpanded = true
                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.search___),
                                    tint = CustomColors.darkPurple,
                                    contentDescription = null
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = isSearchExpanded,
                            enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = spring()),
                            exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = spring())
                        ) {
                            TextField(
                                enabled = true,
                                value = "",
                                onValueChange = {

                                },
                                trailingIcon = {
                                    Icon(
                                        modifier = Modifier.clickable {
                                            isSearchExpanded = false
                                        },
                                        painter = painterResource(id = R.drawable.close),
                                        tint = CustomColors.darkPurple,
                                        contentDescription = null
                                    )
                                },
                                //  label = { Text("جستجو") },
                                // isError = (phone.isEmpty() || phone.length < 11) && sendOtpClicked,

                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester),
                                shape = RoundedCornerShape(30.dp),// Adjust the radius for rounded corners
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color.Transparent, // Set light gray background
                                    cursorColor = CustomColors.darkPurple,
                                    focusedBorderColor = CustomColors.lightGray, // Transparent to clear the outline
                                    unfocusedBorderColor = Color.Transparent // Transparent to clear the outline
                                ),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                AnimatedVisibility(
                    visible = !isSearchExpanded,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                Card(

                    shape= CircleShape,
                    colors=CardDefaults.cardColors(
                        containerColor = CustomColors.lightBlue,
                        contentColor = CustomColors.lightBlue
                    ),


                ) {
                    // Card content: Name and circular image
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                    ) {
                        Text("سعید غفاری",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                            )
                        Spacer(modifier = Modifier.width(10.dp))

                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )

                    }
                }
}
            }
             LazyColumn(
                          modifier = Modifier.weight(1f)
                      ) {
                          // Main lazy column content
                      }

            Card(
                elevation=CardDefaults.cardElevation(20.dp),
                shape= CircleShape,
                colors=CardDefaults.cardColors(
                    containerColor = CustomColors.lightBlue,
                    contentColor = CustomColors.lightBlue
                ),


                ) {
                // Card content: Name and circular image
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding( 5.dp)
                        .fillMaxWidth()
                ) {

                    Text(
                        modifier = Modifier.padding(10.dp),
                       text =  "افزودن رسید جدید",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomColors.darkBlue
                        ),
                        onClick = {

                        },
                    ) {
                        Icon(
                             Icons.Default.Add,
                            tint = Color.White,
                            contentDescription = null
                        )
                    }


                }
            }
            Spacer(modifier = Modifier.size(20.dp))

            /* Card(
                        modifier = Modifier
                            .padding(vertical = 16.dp)

                    ) {
                        // Card content: Name and circular image
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("User Name", style = TextStyle(fontSize = 20.sp))
                        }
                    }*/


        }

        /*      if (isSearchExpanded) {
                AnimatedVisibility(
                    visible = isSearchExpanded,
                    enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = spring()),
                    exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = spring())
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Search") },
                        leadingIcon = {
                            IconButton(onClick = { isSearchExpanded = false }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = null)
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = { *//* Clear search *//* }) {
                                Icon(Icons.Default.Clear, contentDescription = null)
                            }
                        }
                    )
                }
            } else {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { isSearchExpanded = true }
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    // Your other content in the row
                }
            }*/



        /*     Card(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                // Bottom card content: Text and plus button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Bottom Card Content")
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { *//* Handle plus button click *//* }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
            }*/
    }
}

}