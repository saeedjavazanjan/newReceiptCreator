package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    focusRequester: FocusRequester,
    isSearchExpanded: Boolean,
    expandSearchBar: (Boolean) -> Unit,
    modifier: Modifier,
    search: (String) -> Unit
) {

    var searchValue by remember {
        mutableStateOf("")
    }
    Row(modifier = modifier) {
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
                            expandSearchBar(true)
                        },
                    ) {
                        if (!isSearchExpanded) {

                            Icon(
                                painter = painterResource(id = R.drawable.search___),
                                tint = CustomColors.darkPurple,
                                contentDescription = null
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    visible = isSearchExpanded,
                    enter = slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = spring()
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = spring()
                    )
                ) {
                    TextField(
                        enabled = true,
                        value = searchValue,
                        onValueChange = {
                            searchValue = it

                            search(it)
                        },
                        trailingIcon = {
                            if (isSearchExpanded) {
                                Icon(
                                    modifier = Modifier.clickable {
                                        expandSearchBar(false)
                                    },
                                    painter = painterResource(id = R.drawable.close),
                                    tint = CustomColors.darkPurple,
                                    contentDescription = null
                                )
                            }

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

                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = CustomColors.lightBlue,
                    contentColor = CustomColors.lightBlue
                ),


                ) {
                // Card content: Name and circular image
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                ) {
                    Text(
                        "سعید غفاری",
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

}
