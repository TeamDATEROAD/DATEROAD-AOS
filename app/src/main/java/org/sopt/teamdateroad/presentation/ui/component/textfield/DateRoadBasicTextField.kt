package org.sopt.teamdateroad.presentation.ui.component.textfield

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.presentation.ui.component.textfield.model.TextFieldValidateResult
import org.sopt.teamdateroad.presentation.util.modifier.noRippleClickable
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun DateRoadBasicTextField(
    modifier: Modifier = Modifier,
    validateState: TextFieldValidateResult = TextFieldValidateResult.Basic,
    title: String? = null,
    placeholder: String = "",
    @DrawableRes iconResourceId: Int? = null,
    iconContentDescription: String = "",
    successDescription: String = "",
    errorDescription: String = "",
    readOnly: Boolean = false,
    value: String = "",
    onClick: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit = { _ -> },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        title?.let {
            Text(
                text = it,
                color = DateRoadTheme.colors.black,
                style = DateRoadTheme.typography.bodyBold17
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .background(color = DateRoadTheme.colors.gray100, shape = RoundedCornerShape(14.dp))
                .border(width = 1.dp, color = if (validateState == TextFieldValidateResult.ValidationError) DateRoadTheme.colors.alertRed else Color.Transparent, shape = RoundedCornerShape(14.dp))
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (readOnly) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 16.dp)
                        .noRippleClickable(onClick = onClick),
                    text = value.ifEmpty { placeholder },
                    color = if (value.isEmpty()) DateRoadTheme.colors.gray300 else DateRoadTheme.colors.black,
                    style = DateRoadTheme.typography.bodySemi13,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                BasicTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 16.dp)
                        .noRippleClickable(onClick = onClick),
                    value = value,
                    onValueChange = onValueChange,
                    cursorBrush = SolidColor(DateRoadTheme.colors.purple600),
                    singleLine = true,
                    keyboardActions = keyboardActions,
                    keyboardOptions = keyboardOptions,
                    visualTransformation = visualTransformation,
                    textStyle = DateRoadTheme.typography.bodySemi13,
                    decorationBox = { innerTextField ->
                        innerTextField()
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = DateRoadTheme.colors.gray300,
                                style = DateRoadTheme.typography.bodySemi13,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                )
            }
            iconResourceId?.let {
                Spacer(
                    modifier = Modifier.padding(start = 14.dp)
                )
                Icon(painter = painterResource(id = it), contentDescription = iconContentDescription, tint = DateRoadTheme.colors.gray200)
            }
        }
    }
    if (errorDescription.isNotEmpty() || successDescription.isNotEmpty()) {
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 9.dp),
            text = when (validateState) {
                is TextFieldValidateResult.Success -> successDescription
                is TextFieldValidateResult.ValidationError -> errorDescription
                else -> ""
            },
            color = if (validateState == TextFieldValidateResult.ValidationError) DateRoadTheme.colors.alertRed else DateRoadTheme.colors.purple600,
            style = DateRoadTheme.typography.capReg11
        )
    }
}

@Preview
@Composable
fun DateRoadBasicTextFieldPreview() {
    DATEROADTheme {
        var text by remember { mutableStateOf("") }
        var validationState by remember { mutableStateOf<TextFieldValidateResult>(TextFieldValidateResult.Basic) }

        fun validateTest(text: String) {
            validationState = when {
                text.isEmpty() -> TextFieldValidateResult.Basic
                text.length < 5 -> TextFieldValidateResult.ValidationError
                else -> TextFieldValidateResult.Success
            }
        }

        DateRoadBasicTextField(
            validateState = validationState,
            title = "타이틀",
            placeholder = "힌트",
            errorDescription = "최소 5글자 이상 입력해 주세요",
            value = text,
            onValueChange = { newValue ->
                text = newValue
                validateTest(text = newValue)
            }
        )
    }
}
