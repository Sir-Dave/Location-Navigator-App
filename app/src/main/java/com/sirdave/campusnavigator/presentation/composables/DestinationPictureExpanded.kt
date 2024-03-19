package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sirdave.campusnavigator.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DestinationPictureExpanded(pageCount: Int, modifier: Modifier = Modifier){
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val coroutineScope = rememberCoroutineScope()
    var currentPage by remember {
        mutableStateOf(0)
    }
    
    Box(modifier = modifier.fillMaxSize()){
        Column(modifier = modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                HorizontalPager(state = pagerState) {
                    Box(modifier = modifier.fillMaxSize()){
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_close),
                            contentDescription = null,
                            modifier = modifier
                                .align(Alignment.TopStart)
                                .padding(start = 8.dp)
                                .size(25.dp),
                        )

                        Image(
                            painter = painterResource(id = R.drawable.fc4_self_massage),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
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
                        modifier = modifier.weight(1f)
                    ) {
                        coroutineScope.launch {
                            if (currentPage < pageCount - 1)
                                currentPage++

                            pagerState.animateScrollToPage(currentPage)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun LinearIndicator(startProgress: Boolean = false, modifier: Modifier = Modifier, onAnimationEnd: () -> Unit){
    var progress by remember {
        mutableStateOf(0.00f)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    if (startProgress){
        LaunchedEffect(Unit){
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