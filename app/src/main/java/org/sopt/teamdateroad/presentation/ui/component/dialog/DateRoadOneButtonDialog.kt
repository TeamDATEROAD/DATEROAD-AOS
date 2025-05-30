package org.sopt.teamdateroad.presentation.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.presentation.type.OneButtonDialogType
import org.sopt.teamdateroad.presentation.ui.component.button.DateRoadBasicButton
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun DateRoadOneButtonDialog(
    oneButtonDialogType: OneButtonDialogType,
    onDismissRequest: () -> Unit,
    onClickConfirm: () -> Unit
) {
    DateRoadDialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = DateRoadTheme.colors.white),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = DateRoadTheme.colors.black,
                text = stringResource(id = oneButtonDialogType.titleRes),
                style = DateRoadTheme.typography.bodyBold17
            )
            Spacer(modifier = Modifier.height(36.dp))
            DateRoadBasicButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                isEnabled = true,
                textContent = stringResource(id = oneButtonDialogType.buttonTextRes),
                onClick = onClickConfirm
            )
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}
