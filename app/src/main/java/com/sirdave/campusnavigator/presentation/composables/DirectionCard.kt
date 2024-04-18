package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
        modifier = modifier
            .padding(horizontal = 8.dp)
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
                    modifier = modifier
                        .height(50.dp)
                        .width(50.dp)
                )
                Text(
                    text = direction.distanceToNextLocation,
                    color = Color.White
                )
            }

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = modifier.padding(horizontal = 8.dp)
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

@Composable
fun DirectionsToggleCard(
    directions: List<DirectionWithIcon>,
    modifier: Modifier = Modifier
){
    var currentIndex by remember { mutableStateOf(0) }
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ){
            Column(
                modifier = modifier
                    .padding(start = 8.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_left),
                    contentDescription = null,
                    tint = if (currentIndex > 0) Color.White else Color.Gray,
                    modifier = Modifier.clickable {
                        if (currentIndex > 0) {
                            currentIndex--
                        }
                    }
                )
            }
            DirectionCard(
                direction = directions[currentIndex],
                modifier = modifier.weight(1f)
            )

            Column(
                modifier = modifier
                    .padding(end = 8.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right),
                    contentDescription = null,
                    tint = if (currentIndex < directions.size - 1) Color.White else Color.Gray,
                    modifier = Modifier.clickable {
                        if (currentIndex < directions.size - 1) {
                            currentIndex++
                        }
                    }
                )
            }
    }
}

@Preview(showBackground = true)
@Composable
fun DirectionCardPreview(){
    val directions = listOf(
        DirectionWithIcon(
            text = "Move straight ahead towards Queens roundabout Move straight ahead towards Queens roundabout Move straight ahead towards Queens roundabout",
            directionIcon = R.drawable.baseline_roundabout,
            distanceToNextLocation = "200m",
            timeToNextLocation = "30 seconds"
        )
    )
    DirectionsToggleCard(directions = directions)
}