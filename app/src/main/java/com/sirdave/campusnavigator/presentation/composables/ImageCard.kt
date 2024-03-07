package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sirdave.campusnavigator.R

@Composable
fun ImageCard(modifier: Modifier = Modifier){
    OutlinedCard(
        modifier = modifier.padding(8.dp).height(IntrinsicSize.Min).width(IntrinsicSize.Min),
    ) {
        Box(modifier = modifier.fillMaxSize()){
            Image(
                painter = painterResource(id = R.drawable.ab3_stretching),
                contentDescription = null,
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_zoom_out_map),
                contentDescription = null,
                modifier = modifier.align(Alignment.BottomEnd).padding(end = 8.dp, bottom = 8.dp),
                tint = Color.White
            )
        }

    }
}