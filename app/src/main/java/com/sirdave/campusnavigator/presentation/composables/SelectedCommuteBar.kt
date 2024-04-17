package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.presentation.places.PlaceState
import org.osmdroid.bonuspack.routing.OSRMRoadManager

@Composable
fun SelectedCommuteBar(
    placeState: PlaceState,
    onExit: () -> Unit,
    onWalkSelected: () -> Unit,
    onCarSelected: () -> Unit,
    onBikeSelected: () -> Unit,
    modifier: Modifier = Modifier
){
    val selectedMode = placeState.selectedMode
    val selectedColor = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray)
    val unselectedColor = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            CustomIconButton(
                onClick = onWalkSelected,
                modifier = Modifier.weight(1f),
                text = "15 min",
                drawable = R.drawable.baseline_directions_walk,
                buttonColor = if (selectedMode == OSRMRoadManager.MEAN_BY_FOOT) selectedColor else unselectedColor
            )

            Spacer(modifier = modifier.width(4.dp))

            CustomIconButton(
                onClick = onCarSelected,
                modifier = Modifier.weight(1f),
                text = "8 min",
                drawable = R.drawable.baseline_directions_car,
                buttonColor = if (selectedMode == OSRMRoadManager.MEAN_BY_CAR) selectedColor else unselectedColor
            )

            Spacer(modifier = modifier.width(4.dp))

            CustomIconButton(
                onClick = onBikeSelected,
                modifier = Modifier.weight(1f),
                text = "6 min",
                drawable = R.drawable.baseline_directions_bike,
                buttonColor = if (selectedMode == OSRMRoadManager.MEAN_BY_BIKE) selectedColor else unselectedColor
            )
        }

        Spacer(modifier = modifier.height(16.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(modifier = modifier.weight(1f)) {
                Text(
                    text = "Arrival in 15 min",
                    fontSize = 15.sp
                )

                Text(
                    text = "3.5km to go",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Light
                )

            }

            Spacer(modifier = modifier.width(4.dp))

            CustomIconButton(
                onClick = onExit,
                buttonColor = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFFb0040e),
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.exit),
                drawable = R.drawable.baseline_close
            )


        }
    }
    
}



@Preview(showBackground = true)
@Composable
fun SelectedCommuteBarPreview() {
    SelectedCommuteBar(
        placeState = PlaceState(),
        onExit = {},
        onWalkSelected = {},
        onCarSelected = {},
        onBikeSelected = {}
    )
}