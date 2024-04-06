package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.PlaceData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DestinationPictureExpanded(
    selectedPlace: PlaceData,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    val place = selectedPlace.place
    val startIndex = selectedPlace.imageIndex
    val pageCount = place.imageUrls.size
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { pageCount }
    )
    val coroutineScope = rememberCoroutineScope()
    var currentPage by remember {
        mutableStateOf(startIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
    }

    
    Box(modifier = modifier.fillMaxSize()){
        Column(modifier = modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                HorizontalPager(state = pagerState) {
                    Box(modifier = modifier.fillMaxSize()){
                        AsyncImage(
                            model = place.imageUrls[it],
                            contentDescription = null,
                            modifier = modifier.fillMaxSize().background(Color.Black)
                        )

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.TopStart)
                                .padding(8.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable { onBackClicked() }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_close),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(25.dp)
                                    .align(Alignment.Center),
                            )
                        }

                        Text(
                            text = place.name,
                            modifier = modifier.align(Alignment.BottomCenter),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ){

                for (index in 0 until pageCount){
                    LinearIndicator(
                        startProgress = currentPage == index,
                        modifier = modifier.weight(1f),
                        onAnimationEnd = {
                            coroutineScope.launch {
                                if (currentPage < pageCount - 1)
                                    currentPage++

                                else onBackClicked()

                                pagerState.animateScrollToPage(currentPage)
                            }
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun LinearIndicator(
    startProgress: Boolean = false,
    modifier: Modifier = Modifier,
    onAnimationEnd: () -> Unit,
){

    var progress by remember {
        mutableStateOf(0.00f)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    if (startProgress){
        LaunchedEffect(Unit){
            progress = 0.0f
            while (progress < 1f){
                progress += 0.01f
                delay(50)
            }
            onAnimationEnd()
        }
    }

    LinearProgressIndicator(
        color = Color.White,
        trackColor = Color.Gray,
        modifier = modifier
            .background(Color.Black)
            .padding(vertical = 12.dp, horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp)),

        progress = animatedProgress
    )
}