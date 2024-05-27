package com.saeed.zanjan.receipt.utils

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

object NumberFormat {

    fun formatNumber(input: TextFieldValue): TextFieldValue {
        val originalString = input.text.filter { it.isDigit() }
        val formattedString = originalString.reversed().chunked(3).joinToString(",").reversed()

        // Adjust cursor position
        val cursorPosition = formattedString.length - (input.text.length - input.selection.end)
        val adjustedCursorPosition = cursorPosition.coerceIn(0, formattedString.length)

        return TextFieldValue(
            text = formattedString,
            selection = TextRange(adjustedCursorPosition)
        )
    }

}