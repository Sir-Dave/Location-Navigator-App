package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.presentation.places.PlaceState
import org.osmdroid.bonuspack.routing.OSRMRoadManager

@Composable
fun DirectionsBar(
    placeState: PlaceState,
    onBackClick: () -> Unit,
    onWalkSelected: () -> Unit,
    onCarSelected: () -> Unit,
    onBikeSelected: () -> Unit,
    onStartClicked: () -> Unit,
    modifier: Modifier = Modifier
){

    val selectedMode = placeState.selectedMode
    val selectedColor = ButtonDefaults.outlinedButtonColors(containerColor = Color.LightGray)
    val unselectedColor = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = modifier.clickable { onBackClick() }
        )
        
        Spacer(modifier = modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.commute_mode),
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = modifier.height(16.dp))
        
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            CustomIconButton(
                onClick = { onWalkSelected() },
                modifier = Modifier.weight(1f),
                text = "",
                drawable = R.drawable.baseline_directions_walk,
                buttonColor = if (selectedMode == OSRMRoadManager.MEAN_BY_FOOT) selectedColor else unselectedColor
            )

            Spacer(modifier = modifier.width(4.dp))

            CustomIconButton(
                onClick = { onCarSelected() },
                modifier = Modifier.weight(1f),
                text = "",
                drawable = R.drawable.baseline_directions_car,
                buttonColor = if (selectedMode == OSRMRoadManager.MEAN_BY_CAR) selectedColor else unselectedColor
            )

            Spacer(modifier = modifier.width(4.dp))

            CustomIconButton(
                onClick = { onBikeSelected() },
                modifier = Modifier.weight(1f),
                text = "",
                drawable = R.drawable.baseline_directions_bike,
                buttonColor = if (selectedMode == OSRMRoadManager.MEAN_BY_BIKE) selectedColor else unselectedColor
            )
        }

        Spacer(modifier = modifier.height(16.dp))

        CustomIconButton(
            onClick = onStartClicked,
            modifier = Modifier.fillMaxWidth(),
            buttonColor = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            text = stringResource(id = R.string.start),
            drawable = R.drawable.baseline_near_me
        )
    }
    
}



@Preview(showBackground = true)
@Composable
fun DirectionsBarPreview() {
    DirectionsBar(
        placeState = PlaceState(),
        onBackClick = {},
        onWalkSelected = {},
        onCarSelected = {},
        onBikeSelected = {},
        onStartClicked = {}
    )
}