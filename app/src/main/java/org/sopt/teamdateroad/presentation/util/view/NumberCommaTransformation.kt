package org.sopt.teamdateroad.presentation.util.view

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.NumberFormat
import java.util.Locale

class NumberCommaTransformation : VisualTransformation {

    private fun Long?.formatWithComma(): String =
        NumberFormat.getNumberInstance(Locale.US).format(this ?: 0)

    override fun filter(text: AnnotatedString): TransformedText {
        val newText = AnnotatedString(
            text = if (text.text.isEmpty()) "" else text.text.toLongOrNull().formatWithComma()
        )
        return TransformedText(
            text = newText,
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return if (offset != newText.length) newText.length else text.length

                }

                override fun transformedToOriginal(offset: Int): Int {
                    return text.length
                }
            }
        )
    }
}
