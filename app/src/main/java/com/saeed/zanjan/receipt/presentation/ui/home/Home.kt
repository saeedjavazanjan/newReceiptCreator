package com.saeed.zanjan.receipt.presentation.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(){

    var isSearchExpanded by remember { mutableStateOf(false) }

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
                .padding(horizontal = 16.dp)
        ) {
            AnimatedVisibility(
                visible = !isSearchExpanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {

                Row {
                    Button(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CustomColors.CustomLightGray
                        ),
                        onClick = {
                            //    Handle navigation
                        },
                        shape = CircleShape
                    ) {
                        Icon(    painter = painterResource(id = R.drawable.menu),

                            contentDescription = null)
                    }
                    Card(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth()
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
                    }
                }

            }

            if (isSearchExpanded) {
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
                            IconButton(onClick = { /* Clear search */ }) {
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
            }

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                // Main lazy column content
            }

            Card(
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
                    IconButton(onClick = { /* Handle plus button click */ }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
            }
        }
    }
}