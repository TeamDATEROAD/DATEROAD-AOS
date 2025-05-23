package org.sopt.teamdateroad.presentation.ui.component.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.sopt.teamdateroad.presentation.type.TagType

@Composable
fun DateRoadTag(
    modifier: Modifier = Modifier,
    tagType: TagType,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(tagType.roundedCornerShape.dp))
            .background(color = tagType.backgroundColor)
            .padding(horizontal = tagType.paddingHorizontal.dp, vertical = tagType.paddingVertical.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
