package org.sopt.teamdateroad.presentation.ui.enroll.placesearch

import androidx.paging.compose.LazyPagingItems
import org.sopt.teamdateroad.domain.model.PlaceInfo

data class PlaceSearchUiState(
    val searchKeyword: String,
    val searchPlaceInfos: LazyPagingItems<PlaceInfo>
)
