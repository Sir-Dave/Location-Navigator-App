package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sirdave.campusnavigator.R

@Composable
fun ImageCardSmall(modifier: Modifier = Modifier){
    OutlinedCard(
        modifier = modifier.size(85.dp),
    ) {
        Box(modifier = modifier.fillMaxSize()){
            Image(
                painter = painterResource(id = R.drawable.fc4_self_massage),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_zoom_out_map),
                contentDescription = null,
                tint = Color.White,
                modifier = modifier.align(Alignment.BottomEnd)
                    .padding(end = 8.dp, bottom = 8.dp).size(15.dp),
            )
        }

    }
}