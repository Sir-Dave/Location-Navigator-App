package com.sirdave.campusnavigator.presentation.composables

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
import coil.compose.AsyncImage
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.Place

@Composable
fun ImageCard(
    place: Place,
    modifier: Modifier = Modifier
){
    OutlinedCard(
        modifier = modifier.height(IntrinsicSize.Min).width(IntrinsicSize.Min),
    ) {
        Box(modifier = modifier.fillMaxSize()){
            val images = place.imageUrls
            AsyncImage(
                model = if (images.isNotEmpty()) images[0] else "",
                placeholder = painterResource(id = R.drawable.ab3_stretching),
                error = painterResource(id = R.drawable.ab3_stretching),
                contentDescription = null,
                modifier = modifier.height(150.dp).width(180.dp),
                contentScale = ContentScale.Crop
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