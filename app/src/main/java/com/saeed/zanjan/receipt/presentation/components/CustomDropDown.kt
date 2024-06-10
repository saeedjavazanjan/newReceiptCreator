package com.saeed.zanjan.receipt.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdown(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError:Boolean
) {

    var expanded by remember { mutableStateOf(false) }
    val focusRequester = FocusRequester()

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedItem,
            isError=isError,
            onValueChange = { },
            label = { Text(label) },
            trailingIcon = {
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Toggle Dropdown"
                    )
                }
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        expanded = !expanded
                    }
                },
            shape = RoundedCornerShape(30.dp) ,// Adjust the radius for rounded corners
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.Transparent, // Set light gray background
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = CustomColors.lightGray, // Transparent to clear the outline
                unfocusedBorderColor = Color.Transparent // Transparent to clear the outline
            ),
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(300.dp)
                .background(Color.White).align(Alignment.CenterHorizontally)
        ) {
            items.forEach { item ->
                DropdownMenuItem(text = {
                    Text(text = item, style = MaterialTheme.typography.bodyMedium)
                },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}