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
import com.sirdave.campusnavigator.domain.model.Place
import com.sirdave.campusnavigator.domain.model.places

@Composable
fun SelectedCommuteBar(
    place: Place,
    onExit: () -> Unit,
    onWalkSelected: () -> Unit,
    onCarSelected: () -> Unit,
    onBikeSelected: () -> Unit,
    modifier: Modifier = Modifier
){
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
                drawable = R.drawable.baseline_directions_walk
            )

            Spacer(modifier = modifier.width(4.dp))

            CustomIconButton(
                onClick = onCarSelected,
                modifier = Modifier.weight(1f),
                text = "8 min",
                drawable = R.drawable.baseline_directions_car
            )

            Spacer(modifier = modifier.width(4.dp))

            CustomIconButton(
                onClick = onBikeSelected,
                modifier = Modifier.weight(1f),
                text = "6 min",
                drawable = R.drawable.baseline_directions_bike
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
        place = places[0],
        onExit = {},
        onWalkSelected = {},
        onCarSelected = {},
        onBikeSelected = {}
    )
}