package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.R
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaundryFields(
    typeOfLaundryOrder: String,
    detailsOfLaundryOrder: String,
    typeOfLaundryOrderSetValue: (String) -> Unit,
    detailsOfLaundryOrderSetValue: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text(
                modifier = Modifier,
                text = "نوع سفارش",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    },
                value = typeOfLaundryOrder,
                shape = RoundedCornerShape(30.dp),
                onValueChange = { type ->
                    typeOfLaundryOrderSetValue(type)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.color_palette),
                        tint = CustomColors.gray,
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = CustomColors.darkPurple,
                    containerColor = CustomColors.transparentLightGray,
                    cursorColor = CustomColors.lightBlue,
                    focusedBorderColor = CustomColors.lightBlue,
                    unfocusedBorderColor = CustomColors.lightGray
                )
            )

        }
        Column {
            Text(
                modifier = Modifier,
                text = "توضیحات سفارش",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    },
                value = detailsOfLaundryOrder,
                shape = RoundedCornerShape(30.dp),
                onValueChange = { det ->
                    detailsOfLaundryOrderSetValue(det)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.document_align_right_2),
                        tint = CustomColors.gray,
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = CustomColors.darkPurple,
                    containerColor = CustomColors.transparentLightGray,
                    cursorColor = CustomColors.lightBlue,
                    focusedBorderColor = CustomColors.lightBlue,
                    unfocusedBorderColor = CustomColors.lightGray
                )
            )

        }


    }

}

