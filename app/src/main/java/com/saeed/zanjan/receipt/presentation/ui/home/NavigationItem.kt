package com.saeed.zanjan.receipt.presentation.ui.home

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.saeed.zanjan.receipt.R

data class NavigationItem(
    val title:String,
    val icon:Painter,
    val premiumIcon:Painter?=null,
    val premiumState:Boolean=false
)
