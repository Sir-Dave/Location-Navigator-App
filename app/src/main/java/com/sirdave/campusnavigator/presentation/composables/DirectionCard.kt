package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.DirectionWithIcon

@Composable
fun DirectionCard(
    direction: DirectionWithIcon,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 8.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = direction.directionIcon),
                    contentDescription = null,
                    modifier = modifier.height(70.dp).width(70.dp)
                )
                Text(
                    text = direction.distanceToNextLocation,
                    color = Color.White
                )
            }
            Spacer(modifier = modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = direction.text,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                )
                Text(
                    text = direction.timeToNextLocation,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DirectionCardPreview(){
    DirectionCard(
        direction = DirectionWithIcon(
            text = "Move straight ahead towards Queens roundabout Move straight ahead towards Queens roundabout Move straight ahead towards Queens roundabout",
            directionIcon = R.drawable.baseline_roundabout,
            distanceToNextLocation = "200m",
            timeToNextLocation = "30 seconds"
        )
    )
}