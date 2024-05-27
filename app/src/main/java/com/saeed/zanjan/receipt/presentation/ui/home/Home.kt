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
import com.saeed.zanjan.receipt.presentation.components.AddReceiptCard
import com.saeed.zanjan.receipt.presentation.components.HomeTopBar
import com.saeed.zanjan.receipt.presentation.navigation.Screen
import com.saeed.zanjan.receipt.ui.theme.CustomColors
import com.saeed.zanjan.receipt.ui.theme.NewReceiptCreatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(
    viewModel: HomeViewModel,
    navigateToReceiptScreen:(String)->Unit
) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(isSearchExpanded) {
        if (isSearchExpanded) {
            focusRequester.requestFocus()
        }
    }
    NewReceiptCreatorTheme(
        displayProgressBar=false,
        themColor = Color.Transparent
    ) {
    Scaffold(
         topBar ={
             HomeTopBar(
                 modifier = Modifier.padding(15.dp),
                 focusRequester = focusRequester,
                 isSearchExpanded=isSearchExpanded,
                 expandSearchBar = {
                     isSearchExpanded=it
                 }
             )
         },
        bottomBar = {
            AddReceiptCard(
                modifier = Modifier.padding(20.dp),
                addButtonClicked = {
                    val navType="add"
                    val receiptCategory=1
                    val route = Screen.Receipt.route + "/${-1}/${navType}/${receiptCategory}"
                    navigateToReceiptScreen(route)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
        ) {



            ListOfReceipts(modifier = Modifier.weight(1f))



            Spacer(modifier = Modifier.size(20.dp))



        }


    }
}

}
@Composable
fun ListOfReceipts(modifier: Modifier){
    LazyColumn(modifier = modifier
    ) {
        // Main lazy column content
    }

}


