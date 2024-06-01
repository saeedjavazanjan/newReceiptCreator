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
fun JewelryFields(
    jewelryProblem: String,
    explainOfOrder: String,
    detailsOfProduct: String,
    jewelryProblemSetValue: (String) -> Unit,
    explainOfOrderSetValue: (String) -> Unit,
    detailOfProductSetValue: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text(
                modifier = Modifier,
                text = "مشکل محصول",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    },
                value = jewelryProblem,
                shape = RoundedCornerShape(30.dp),
                onValueChange = { problem ->
                    jewelryProblemSetValue(problem)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.settings),
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
                text = "توضیحات سفارش ساخت",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    },
                value = explainOfOrder,
                shape = RoundedCornerShape(30.dp),
                onValueChange = { explain ->
                    explainOfOrderSetValue(explain)
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
        Column {
            Text(
                modifier = Modifier,
                text = "مشخصات محصول تعمیری",
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors.bitterDarkPurple
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = detailsOfProduct,
                shape = RoundedCornerShape(30.dp),
                onValueChange = { det ->
                    detailOfProductSetValue(det)
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

