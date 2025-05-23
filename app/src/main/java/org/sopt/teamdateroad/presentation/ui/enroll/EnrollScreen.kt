package org.sopt.teamdateroad.presentation.ui.enroll

import android.net.Uri
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.sopt.teamdateroad.R
import org.sopt.teamdateroad.domain.model.Place
import org.sopt.teamdateroad.domain.model.PlaceInfo
import org.sopt.teamdateroad.domain.type.RegionType
import org.sopt.teamdateroad.presentation.type.DateRoadRegionBottomSheetType
import org.sopt.teamdateroad.presentation.type.DateTagType
import org.sopt.teamdateroad.presentation.type.EnrollScreenType
import org.sopt.teamdateroad.presentation.type.EnrollType
import org.sopt.teamdateroad.presentation.type.MyCourseType
import org.sopt.teamdateroad.presentation.type.OneButtonDialogType
import org.sopt.teamdateroad.presentation.type.OneButtonDialogWithDescriptionType
import org.sopt.teamdateroad.presentation.ui.component.bottomsheet.DateRoadDatePickerBottomSheet
import org.sopt.teamdateroad.presentation.ui.component.bottomsheet.DateRoadPickerBottomSheet
import org.sopt.teamdateroad.presentation.ui.component.bottomsheet.DateRoadRegionBottomSheet
import org.sopt.teamdateroad.presentation.ui.component.button.DateRoadBasicButton
import org.sopt.teamdateroad.presentation.ui.component.button.DateRoadFilledButton
import org.sopt.teamdateroad.presentation.ui.component.dialog.DateRoadOneButtonDialog
import org.sopt.teamdateroad.presentation.ui.component.dialog.DateRoadOneButtonDialogWithDescription
import org.sopt.teamdateroad.presentation.ui.component.textfield.model.TextFieldValidateResult
import org.sopt.teamdateroad.presentation.ui.component.topbar.DateRoadBasicTopBar
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadErrorView
import org.sopt.teamdateroad.presentation.ui.component.view.DateRoadLoadingView
import org.sopt.teamdateroad.presentation.ui.enroll.component.EnrollPhotos
import org.sopt.teamdateroad.presentation.util.DatePicker
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.CLICK_BRING_COURSE
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.CLICK_COURSE1_BACK
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.CLICK_COURSE2_BACK
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.CLICK_COURSE3_BACK
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.CLICK_SCHEDULE1_BACK
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.CLICK_SCHEDULE2_BACK
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.COURSE_CONTENT_BOOL
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.COURSE_CONTENT_NUM
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.COURSE_COST
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.COURSE_DATE
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.COURSE_IMAGE
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.COURSE_LOCATION
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.COURSE_START_TIME
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.COURSE_TAGS
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.COURSE_TITLE
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.DATE_AREA
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.DATE_COURSE_NUM
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.DATE_DATE
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.DATE_DETAIL_LOCATION
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.DATE_DETAIL_TIME
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.DATE_LOCATION
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.DATE_SPEND_TIME
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.DATE_TAG_NUM
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.DATE_TIME
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.DATE_TITLE
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.LOCATION_NUM
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.VIEW_ADD_BRING_COURSE
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.VIEW_ADD_BRING_COURSE2
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.VIEW_ADD_SCHEDULE
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.VIEW_ADD_SCHEDULE2
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.VIEW_COURSE1
import org.sopt.teamdateroad.presentation.util.EnrollAmplitude.VIEW_PATH
import org.sopt.teamdateroad.presentation.util.EnrollScreen.MAX_ITEMS
import org.sopt.teamdateroad.presentation.util.EnrollScreen.TITLE_MIN_LENGTH
import org.sopt.teamdateroad.presentation.util.GalleryLauncher.INPUT
import org.sopt.teamdateroad.presentation.util.TimePicker
import org.sopt.teamdateroad.presentation.util.TimelineAmplitude.CLICK_ADD_SCHEDULE
import org.sopt.teamdateroad.presentation.util.amplitude.AmplitudeUtils
import org.sopt.teamdateroad.presentation.util.view.LoadState
import org.sopt.teamdateroad.ui.theme.DATEROADTheme
import org.sopt.teamdateroad.ui.theme.DateRoadTheme

@Composable
fun EnrollRoute(
    padding: PaddingValues,
    viewModel: EnrollViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateToMyCourse: (MyCourseType) -> Unit,
    enrollType: EnrollType,
    viewPath: String,
    timelineId: Int?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchKeyword by viewModel.searchKeyword.collectAsStateWithLifecycle("")
    val searchPlaceInfos = viewModel.searchPlaceInfos.collectAsLazyPagingItems()

    val lifecycleOwner = LocalLifecycleOwner.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val getGalleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) viewModel.setEvent(EnrollContract.EnrollEvent.SetImage(images = listOf(uri.toString())))
    }

    val getPhotoPickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxItems = MAX_ITEMS)) { uris: List<Uri> ->
        viewModel.setEvent(EnrollContract.EnrollEvent.SetImage(images = uris.map { it.toString() }))
    }

    BackHandler {
        viewModel.setEvent(EnrollContract.EnrollEvent.OnTopBarBackButtonClick)
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(EnrollContract.EnrollEvent.FetchEnrollCourseType(enrollType = enrollType))

        if (timelineId != null) {
            when (enrollType) {
                EnrollType.COURSE -> {
                    viewModel.fetchTimelineDetail(timelineId = timelineId)
                }

                EnrollType.TIMELINE -> {
                    viewModel.fetchCourseDetail(courseId = timelineId)
                }
            }
        }
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { enrollSideEffect ->
                when (enrollSideEffect) {
                    is EnrollContract.EnrollSideEffect.PopBackStack -> popBackStack()
                    is EnrollContract.EnrollSideEffect.NavigateToMyCourseRead -> navigateToMyCourse(MyCourseType.READ)
                }
            }
    }

    LaunchedEffect(uiState.enroll.title) {
        viewModel.setEvent(
            EnrollContract.EnrollEvent.SetTitleValidationState(
                titleValidationState = when {
                    uiState.enroll.title.isEmpty() -> TextFieldValidateResult.Basic
                    uiState.enroll.title.length >= TITLE_MIN_LENGTH -> TextFieldValidateResult.Success
                    else -> TextFieldValidateResult.ValidationError
                }
            )
        )
    }

    LaunchedEffect(uiState.enroll.date) {
        viewModel.setEvent(
            EnrollContract.EnrollEvent.SetDateValidationState(
                dateValidationState = when {
                    uiState.enroll.date.isEmpty() -> TextFieldValidateResult.Basic
                    uiState.enrollType == EnrollType.COURSE && LocalDate.parse(uiState.enroll.date, DateTimeFormatter.ofPattern(DatePicker.DATE_PATTERN)).isAfter(LocalDate.now()) -> TextFieldValidateResult.ValidationError
                    else -> TextFieldValidateResult.Success
                }
            )
        )
    }

    LaunchedEffect(uiState.page) {
        when (enrollType) {
            EnrollType.COURSE -> {
                when (uiState.page) {
                    EnrollScreenType.FIRST -> AmplitudeUtils.trackEventWithProperty(eventName = VIEW_COURSE1, propertyName = VIEW_PATH, propertyValue = viewPath)
                    EnrollScreenType.SECOND -> Unit
                    EnrollScreenType.THIRD -> Unit
                }
            }

            EnrollType.TIMELINE -> {
                (timelineId != null).let { isBringCourse ->
                    when (uiState.page) {
                        EnrollScreenType.FIRST -> AmplitudeUtils.trackEventWithProperty(eventName = if (isBringCourse) VIEW_ADD_BRING_COURSE else VIEW_ADD_SCHEDULE, propertyName = VIEW_PATH, propertyValue = viewPath)
                        EnrollScreenType.SECOND -> AmplitudeUtils.trackEvent(eventName = if (isBringCourse) VIEW_ADD_BRING_COURSE2 else VIEW_ADD_SCHEDULE2)
                        EnrollScreenType.THIRD -> Unit
                    }
                }
            }
        }
    }

    LaunchedEffect(uiState.loadState) {
        if (uiState.loadState == LoadState.Success) {
            when (uiState.enrollType) {
                EnrollType.TIMELINE -> AmplitudeUtils.trackEvent(CLICK_ADD_SCHEDULE)
                else -> Unit
            }
        }
    }

    EnrollScreen(
        padding = padding,
        enrollUiState = uiState,
        searchKeyword = searchKeyword,
        searchPlaceInfos = searchPlaceInfos.itemSnapshotList.items,
        onTopBarBackButtonClick = {
            viewModel.setEvent(EnrollContract.EnrollEvent.OnTopBarBackButtonClick)

            when (enrollType) {
                EnrollType.COURSE -> {
                    when (uiState.page) {
                        EnrollScreenType.FIRST -> AmplitudeUtils.trackEventWithProperties(
                            eventName = CLICK_COURSE1_BACK,
                            properties = with(uiState.enroll) { mapOf(COURSE_IMAGE to images.isNotEmpty(), COURSE_TITLE to title.isNotEmpty(), COURSE_DATE to date.isNotEmpty(), COURSE_START_TIME to startAt.isNotEmpty(), COURSE_TAGS to tags.isNotEmpty(), COURSE_LOCATION to (city != null)) }
                        )

                        EnrollScreenType.SECOND -> AmplitudeUtils.trackEventWithProperties(
                            eventName = CLICK_COURSE2_BACK,
                            properties = with(uiState.place) { mapOf(DATE_LOCATION to title.isNotEmpty(), DATE_SPEND_TIME to duration.isNotEmpty(), LOCATION_NUM to uiState.enroll.places.size) }
                        )

                        EnrollScreenType.THIRD -> AmplitudeUtils.trackEventWithProperties(
                            eventName = CLICK_COURSE3_BACK,
                            properties = with(uiState.enroll) { mapOf(COURSE_CONTENT_BOOL to description.isNotEmpty(), COURSE_CONTENT_NUM to description.length, COURSE_COST to cost.isNotEmpty()) }
                        )
                    }
                }

                EnrollType.TIMELINE -> {
                    when (uiState.page) {
                        EnrollScreenType.FIRST -> AmplitudeUtils.trackEventWithProperties(
                            eventName = CLICK_SCHEDULE1_BACK,
                            properties = with(uiState.enroll) { mapOf(DATE_TITLE to title.isNotEmpty(), DATE_DATE to date.isNotEmpty(), DATE_TIME to startAt.isNotEmpty(), DATE_TAG_NUM to tags.size, DATE_AREA to (city != null)) }
                        )

                        EnrollScreenType.SECOND -> AmplitudeUtils.trackEventWithProperties(
                            eventName = CLICK_SCHEDULE2_BACK,
                            properties = with(uiState.place) { mapOf(DATE_DETAIL_LOCATION to title.isNotEmpty(), DATE_DETAIL_TIME to duration.isNotEmpty(), DATE_COURSE_NUM to uiState.enroll.places.size) }
                        )

                        EnrollScreenType.THIRD -> Unit
                    }
                }
            }
        },
        onTopBarLoadButtonClick = {
            viewModel.setSideEffect(EnrollContract.EnrollSideEffect.NavigateToMyCourseRead)
            AmplitudeUtils.trackEvent(eventName = CLICK_BRING_COURSE)
        },
        onEnrollButtonClick = { viewModel.setEvent(EnrollContract.EnrollEvent.OnEnrollButtonClick) },
        onDateTextFieldClick = {
            keyboardController?.hide()
            viewModel.setEvent(EnrollContract.EnrollEvent.OnDateTextFieldClick)
        },
        onTimeTextFieldClick = {
            keyboardController?.hide()
            viewModel.setEvent(EnrollContract.EnrollEvent.OnTimeTextFieldClick)
        },
        onRegionTextFieldClick = {
            keyboardController?.hide()
            viewModel.setEvent(EnrollContract.EnrollEvent.OnRegionTextFieldClick)
        },
        onPlaceSearchButtonClick = { viewModel.setEvent(EnrollContract.EnrollEvent.OnPlaceSearchButtonClick) },
        onKeywordChanged = { keyword -> viewModel.setEvent(EnrollContract.EnrollEvent.OnKeywordChanged(keyword = keyword)) },
        onPlaceSelected = { placeInfo -> viewModel.setEvent(EnrollContract.EnrollEvent.OnPlaceSelected(placeInfo = placeInfo)) },
        onPlaceSearchBottomSheetDismiss = { viewModel.setEvent(EnrollContract.EnrollEvent.OnPlaceSearchBottomSheetDismiss) },
        onSelectedPlaceCourseTimeClick = { viewModel.setEvent(EnrollContract.EnrollEvent.OnSelectedPlaceCourseTimeClick) },
        onDatePickerBottomSheetDismissRequest = { viewModel.setEvent(EnrollContract.EnrollEvent.OnDatePickerBottomSheetDismissRequest) },
        onTimePickerBottomSheetDismissRequest = { viewModel.setEvent(EnrollContract.EnrollEvent.OnTimePickerBottomSheetDismissRequest) },
        onRegionBottomSheetDismissRequest = { viewModel.setEvent(EnrollContract.EnrollEvent.OnRegionBottomSheetDismissRequest) },
        onDurationBottomSheetDismissRequest = { viewModel.setEvent(EnrollContract.EnrollEvent.OnDurationBottomSheetDismissRequest) },
        onPhotoButtonClick = {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                getGalleryLauncher.launch(INPUT)
            } else {
                getPhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        },
        onImageDeleteButtonClick = { index ->
            viewModel.setEvent(
                EnrollContract.EnrollEvent.OnImageDeleteButtonClick(
                    index = index,
                    moveThumbnail = index <= uiState.thumbnailIndex
                )
            )
        },
        onTitleValueChange = { title -> viewModel.setEvent(EnrollContract.EnrollEvent.OnTitleValueChange(title = title)) },
        onDatePickerBottomSheetButtonClick = { date -> viewModel.setEvent(EnrollContract.EnrollEvent.OnDatePickerBottomSheetButtonClick(date = date)) },
        onTimePickerBottomSheetButtonClick = { startAt -> viewModel.setEvent(EnrollContract.EnrollEvent.OnTimePickerBottomSheetButtonClick(startAt = startAt)) },
        onDateChipClicked = { tag -> viewModel.setEvent(EnrollContract.EnrollEvent.OnDateChipClicked(tag = tag.name)) },
        onRegionBottomSheetRegionChipClick = { country -> viewModel.setEvent(EnrollContract.EnrollEvent.OnRegionBottomSheetRegionChipClick(country = country)) },
        onRegionBottomSheetAreaChipClick = { city -> viewModel.setEvent(EnrollContract.EnrollEvent.OnRegionBottomSheetAreaChipClick(city = city)) },
        onRegionBottomSheetButtonClick = { region: RegionType?, area: Any? -> viewModel.setEvent(EnrollContract.EnrollEvent.OnRegionBottomSheetButtonClick(region = region, area = area)) },
        onAddPlaceButtonClick = { place -> viewModel.setEvent(EnrollContract.EnrollEvent.OnAddPlaceButtonClick(place = place)) },
        onPlaceCardDragAndDrop = { places -> viewModel.setEvent(EnrollContract.EnrollEvent.OnPlaceCardDragAndDrop(places = places)) },
        onDurationBottomSheetButtonClick = { placeDuration -> viewModel.setEvent(EnrollContract.EnrollEvent.OnDurationBottomSheetButtonClick(placeDuration = placeDuration)) },
        onPlaceEditButtonClick = { editable -> viewModel.setEvent(EnrollContract.EnrollEvent.OnEditableValueChange(editable = editable)) },
        onPlaceCardDeleteButtonClick = { index -> viewModel.setEvent(EnrollContract.EnrollEvent.OnPlaceCardDeleteButtonClick(index = index)) },
        onDescriptionValueChange = { description -> viewModel.setEvent(EnrollContract.EnrollEvent.OnDescriptionValueChange(description = description)) },
        onCostValueChange = { cost -> viewModel.setEvent(EnrollContract.EnrollEvent.OnCostValueChange(cost = cost)) },
        onEnrollSuccessDialogButtonClick = { viewModel.setSideEffect(EnrollContract.EnrollSideEffect.PopBackStack) },
        onSelectThumbnail = { viewModel.setEvent(EnrollContract.EnrollEvent.OnSelectThumbnail(index = it)) }
    )

    when (uiState.loadState) {
        LoadState.Success -> {
            viewModel.setEvent(EnrollContract.EnrollEvent.SetIsEnrollSuccessDialogOpen(isEnrollSuccessDialogOpen = true))
        }

        LoadState.Loading -> DateRoadLoadingView()

        LoadState.Error -> DateRoadErrorView()

        else -> Unit
    }

    with(uiState) {
        viewModel.setEvent(
            EnrollContract.EnrollEvent.SetEnrollButtonEnabled(
                when (page) {
                    EnrollScreenType.FIRST -> {
                        when (enrollType) {
                            EnrollType.COURSE -> enroll.images.isNotEmpty() && titleValidateState == TextFieldValidateResult.Success && dateValidateState == TextFieldValidateResult.Success && enroll.startAt.isNotEmpty() && enroll.tags.isNotEmpty() && enroll.country != null && enroll.city != null
                            EnrollType.TIMELINE -> titleValidateState == TextFieldValidateResult.Success && enroll.date.isNotEmpty() && enroll.startAt.isNotEmpty() && enroll.tags.isNotEmpty() && enroll.country != null && enroll.city != null
                        }
                    }

                    EnrollScreenType.SECOND -> enroll.places.size >= 2
                    EnrollScreenType.THIRD -> enroll.description.length >= 200 && enroll.cost.isNotEmpty()
                }
            )
        )

        if (enroll.places.isEmpty()) viewModel.setEvent(EnrollContract.EnrollEvent.OnEditableValueChange(editable = true))
    }
}

@Composable
fun EnrollScreen(
    padding: PaddingValues,
    enrollUiState: EnrollContract.EnrollUiState = EnrollContract.EnrollUiState(),
    searchKeyword: String,
    searchPlaceInfos: List<PlaceInfo>,
    onTopBarBackButtonClick: () -> Unit,
    onTopBarLoadButtonClick: () -> Unit,
    onEnrollButtonClick: () -> Unit,
    onDateTextFieldClick: () -> Unit,
    onTimeTextFieldClick: () -> Unit,
    onRegionTextFieldClick: () -> Unit,
    onPlaceSearchButtonClick: () -> Unit,
    onKeywordChanged: (String) -> Unit,
    onPlaceSelected: (PlaceInfo) -> Unit,
    onPlaceSearchBottomSheetDismiss: () -> Unit,
    onSelectedPlaceCourseTimeClick: () -> Unit,
    onDatePickerBottomSheetDismissRequest: () -> Unit,
    onTimePickerBottomSheetDismissRequest: () -> Unit,
    onRegionBottomSheetDismissRequest: () -> Unit,
    onDurationBottomSheetDismissRequest: () -> Unit,
    onPhotoButtonClick: () -> Unit,
    onImageDeleteButtonClick: (Int) -> Unit,
    onTitleValueChange: (String) -> Unit,
    onDatePickerBottomSheetButtonClick: (String) -> Unit,
    onTimePickerBottomSheetButtonClick: (String) -> Unit,
    onDateChipClicked: (DateTagType) -> Unit,
    onRegionBottomSheetRegionChipClick: (RegionType) -> Unit,
    onRegionBottomSheetAreaChipClick: (Any?) -> Unit,
    onRegionBottomSheetButtonClick: (RegionType?, Any?) -> Unit,
    onAddPlaceButtonClick: (Place) -> Unit,
    onPlaceCardDragAndDrop: (List<Place>) -> Unit,
    onDurationBottomSheetButtonClick: (String) -> Unit,
    onPlaceEditButtonClick: (Boolean) -> Unit,
    onPlaceCardDeleteButtonClick: (Int) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    onCostValueChange: (String) -> Unit,
    onEnrollSuccessDialogButtonClick: () -> Unit,
    onSelectThumbnail: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(color = DateRoadTheme.colors.white)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        when (enrollUiState.enrollType) {
            EnrollType.COURSE -> {
                DateRoadBasicTopBar(
                    title = stringResource(id = R.string.top_bar_title_enroll_course),
                    leftIconResource = R.drawable.ic_top_bar_back_white,
                    backGroundColor = DateRoadTheme.colors.white,
                    onLeftIconClick = onTopBarBackButtonClick
                )
                Spacer(modifier = Modifier.height(8.dp))
                EnrollPhotos(
                    isEditable = enrollUiState.page == EnrollScreenType.FIRST,
                    images = enrollUiState.enroll.images,
                    thumbnailIndex = enrollUiState.thumbnailIndex,
                    onPhotoButtonClick = onPhotoButtonClick,
                    onDeleteButtonClick = onImageDeleteButtonClick,
                    onSelectThumbnail = onSelectThumbnail
                )
            }

            EnrollType.TIMELINE -> {
                DateRoadBasicTopBar(
                    title = stringResource(id = R.string.top_bar_title_enroll_timeline),
                    leftIconResource = R.drawable.ic_top_bar_back_white,
                    onLeftIconClick = onTopBarBackButtonClick,
                    buttonContent = {
                        if (enrollUiState.page == EnrollScreenType.FIRST) {
                            Row {
                                DateRoadFilledButton(
                                    isEnabled = true,
                                    textContent = stringResource(id = R.string.top_bar_button_text_load),
                                    onClick = onTopBarLoadButtonClick,
                                    textStyle = DateRoadTheme.typography.bodyMed13,
                                    enabledBackgroundColor = DateRoadTheme.colors.purple600,
                                    enabledTextColor = DateRoadTheme.colors.white,
                                    disabledBackgroundColor = DateRoadTheme.colors.gray200,
                                    disabledTextColor = DateRoadTheme.colors.gray400,
                                    cornerRadius = 20.dp,
                                    paddingHorizontal = 10.dp,
                                    paddingVertical = 5.dp
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            when (enrollUiState.page) {
                EnrollScreenType.FIRST -> EnrollFirstScreen(
                    enrollUiState = enrollUiState,
                    onDateTextFieldClick = onDateTextFieldClick,
                    onTimeTextFieldClick = onTimeTextFieldClick,
                    onRegionTextFieldClick = onRegionTextFieldClick,
                    onTitleValueChange = onTitleValueChange,
                    onDateChipClicked = onDateChipClicked
                )

                EnrollScreenType.SECOND -> EnrollSecondScreen(
                    enrollUiState = enrollUiState,
                    searchKeyword = searchKeyword,
                    searchPlaceInfos = searchPlaceInfos,
                    onPlaceSearchButtonClick = onPlaceSearchButtonClick,
                    onKeywordChanged = onKeywordChanged,
                    onPlaceSelected = onPlaceSelected,
                    onPlaceSearchBottomSheetDismiss = onPlaceSearchBottomSheetDismiss,
                    onSelectedPlaceCourseTimeClick = onSelectedPlaceCourseTimeClick,
                    onAddPlaceButtonClick = onAddPlaceButtonClick,
                    onPlaceEditButtonClick = onPlaceEditButtonClick,
                    onPlaceCardDeleteButtonClick = onPlaceCardDeleteButtonClick,
                    onPlaceCardDragAndDrop = onPlaceCardDragAndDrop
                )

                EnrollScreenType.THIRD -> EnrollThirdScreen(
                    enrollUiState = enrollUiState,
                    onDescriptionValueChange = onDescriptionValueChange,
                    onCostValueChange = onCostValueChange
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .padding(horizontal = 16.dp)
        )
        DateRoadBasicButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            isEnabled = enrollUiState.isEnrollButtonEnabled,
            textContent = when (enrollUiState.enrollType) {
                EnrollType.COURSE -> if (enrollUiState.page != EnrollScreenType.THIRD) stringResource(id = R.string.enroll_button_text_next_with_page, enrollUiState.page.position, 3) else stringResource(id = R.string.complete)
                EnrollType.TIMELINE -> if (enrollUiState.page == EnrollScreenType.FIRST) stringResource(id = R.string.enroll_button_text_next) else stringResource(id = R.string.complete)
            },
            onClick = onEnrollButtonClick
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

    DateRoadDatePickerBottomSheet(
        isBottomSheetOpen = enrollUiState.isDatePickerBottomSheetOpen,
        isButtonEnabled = true,
        buttonText = stringResource(id = R.string.apply),
        onDatePickerBottomSheetButtonClick = onDatePickerBottomSheetButtonClick,
        onDismissRequest = onDatePickerBottomSheetDismissRequest
    )

    DateRoadPickerBottomSheet(
        isBottomSheetOpen = enrollUiState.isTimePickerBottomSheetOpen,
        isButtonEnabled = true,
        buttonText = stringResource(id = R.string.apply),
        onButtonClick = {
            onTimePickerBottomSheetButtonClick(
                formatTime(enrollUiState.timePickers.map { it.pickerState.selectedItem })
            )
        },
        onDismissRequest = onTimePickerBottomSheetDismissRequest,
        pickers = enrollUiState.timePickers
    )

    DateRoadRegionBottomSheet(
        isBottomSheetOpen = enrollUiState.isRegionBottomSheetOpen,
        isButtonEnabled = enrollUiState.onRegionBottomSheetRegionSelected != null && enrollUiState.onRegionBottomSheetAreaSelected != null,
        dateRoadRegionBottomSheetType = DateRoadRegionBottomSheetType.ENROLL,
        selectedRegion = enrollUiState.onRegionBottomSheetRegionSelected,
        onSelectedRegionChanged = { regionType ->
            onRegionBottomSheetRegionChipClick(regionType)
        },
        selectedArea = enrollUiState.onRegionBottomSheetAreaSelected,
        onSelectedAreaChanged = { area ->
            onRegionBottomSheetAreaChipClick(area)
        },
        titleText = stringResource(id = R.string.region_bottom_sheet_title),
        buttonText = stringResource(id = R.string.apply),
        onButtonClick = { region, area -> onRegionBottomSheetButtonClick(region, area) },
        onDismissRequest = onRegionBottomSheetDismissRequest
    )

    DateRoadPickerBottomSheet(
        isBottomSheetOpen = enrollUiState.isDurationBottomSheetOpen,
        isButtonEnabled = true,
        buttonText = stringResource(id = R.string.apply),
        onButtonClick = {
            onDurationBottomSheetButtonClick(enrollUiState.durationPicker.first().pickerState.selectedItem)
        },
        onDismissRequest = onDurationBottomSheetDismissRequest,
        pickers = enrollUiState.durationPicker
    )

    if (enrollUiState.isEnrollSuccessDialogOpen) {
        when (enrollUiState.enrollType) {
            EnrollType.TIMELINE -> {
                DateRoadOneButtonDialog(
                    oneButtonDialogType = OneButtonDialogType.ENROLL_TIMELINE,
                    onDismissRequest = onEnrollSuccessDialogButtonClick,
                    onClickConfirm = onEnrollSuccessDialogButtonClick
                )
            }

            EnrollType.COURSE -> {
                DateRoadOneButtonDialogWithDescription(
                    oneButtonDialogWithDescriptionType = OneButtonDialogWithDescriptionType.ENROLL_COURSE,
                    onDismissRequest = onEnrollSuccessDialogButtonClick,
                    onClickConfirm = onEnrollSuccessDialogButtonClick
                )
            }
        }
    }
}

fun formatTime(time: List<String>): String {
    val period = if (time[0] == TimePicker.AM) TimePicker.AM_ENG else TimePicker.PM_ENG
    val hour = time[1].padStart(2, '0')
    val minute = time[2].padStart(2, '0')
    return "$hour:$minute $period"
}

@Preview
@Composable
fun EnrollScreenPreview() {
    DATEROADTheme {
        EnrollScreen(
            padding = PaddingValues(0.dp),
            enrollUiState = EnrollContract.EnrollUiState(
                loadState = LoadState.Success
            ),
            searchKeyword = "",
            searchPlaceInfos = emptyList(),
            onTopBarBackButtonClick = {},
            onTopBarLoadButtonClick = {},
            onEnrollButtonClick = {},
            onDateTextFieldClick = {},
            onTimeTextFieldClick = {},
            onRegionTextFieldClick = {},
            onPlaceSearchButtonClick = {},
            onSelectedPlaceCourseTimeClick = {},
            onDatePickerBottomSheetDismissRequest = {},
            onTimePickerBottomSheetDismissRequest = {},
            onRegionBottomSheetDismissRequest = {},
            onDurationBottomSheetDismissRequest = {},
            onPhotoButtonClick = {},
            onImageDeleteButtonClick = {},
            onTitleValueChange = {},
            onDatePickerBottomSheetButtonClick = {},
            onTimePickerBottomSheetButtonClick = {},
            onDateChipClicked = {},
            onRegionBottomSheetRegionChipClick = {},
            onRegionBottomSheetAreaChipClick = {},
            onRegionBottomSheetButtonClick = { _, _ -> },
            onAddPlaceButtonClick = {},
            onDurationBottomSheetButtonClick = {},
            onPlaceEditButtonClick = {},
            onPlaceCardDeleteButtonClick = {},
            onPlaceCardDragAndDrop = {},
            onDescriptionValueChange = {},
            onCostValueChange = {},
            onEnrollSuccessDialogButtonClick = {},
            onSelectThumbnail = {},
            onPlaceSearchBottomSheetDismiss = {},
            onKeywordChanged = { },
            onPlaceSelected = { }
        )
    }
}
