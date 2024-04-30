package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.PlaceData
import com.sirdave.campusnavigator.presentation.places.PlaceState
import com.sirdave.campusnavigator.util.formatDistance

@Composable
fun DestinationDetail(
    state: PlaceState,
    onViewFullScreen: (PlaceData) -> Unit,
    onBackClicked: () -> Unit,
    onDirect: () -> Unit,
    modifier: Modifier = Modifier
){
    val selectedPlace = state.currentPlace
    selectedPlace?.let {
        Column(
            modifier = modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = modifier.clickable { onBackClicked() }
            )

            Spacer(modifier = modifier.height(16.dp))

            LazyRow(modifier = modifier){
                itemsIndexed(selectedPlace.imageUrls){ index, _ ->
                    ImageCard(
                        place = selectedPlace,
                        imageIndex = index,
                        onViewFullScreen = onViewFullScreen,
                    )
                    Spacer(modifier = modifier.width(8.dp))
                }
            }

            Text(
                text = selectedPlace.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3
            )

            Row(modifier = modifier.padding(top = 8.dp)){
                ConstraintLayout {
                    val (locationIcon, locationText) = createRefs()

                    Icon(
                        painter = painterResource(id = R.drawable.baseline_location),
                        contentDescription = null,
                        modifier = modifier.constrainAs(locationIcon){
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                    )
                    val road = state.road
                    road?.let {
                        Text(
                            text = formatDistance(road.mLength),
                            modifier = modifier.constrainAs(locationText){
                                bottom.linkTo(parent.bottom)
                                start.linkTo(locationIcon.end)
                            },
                            fontSize = 15.sp
                        )
                    }

                }

                Spacer(modifier = modifier.width(16.dp))

                ConstraintLayout {
                    val (directionIcon, directionText) = createRefs()

                    Icon(
                        painter = painterResource(id = R.drawable.baseline_location_city),
                        contentDescription = null,
                        modifier = modifier.constrainAs(directionIcon){
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                    )
                    Text(
                        text = selectedPlace.placeType,
                        modifier = modifier.constrainAs(directionText){
                            bottom.linkTo(parent.bottom)
                            start.linkTo(directionIcon.end)
                        },
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = modifier.height(16.dp))

            CustomIconButton(
                onClick = onDirect,
                buttonColor = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.direct_me),
                drawable = R.drawable.baseline_near_me
            )
        }
    }
    
}



@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    DestinationDetail(
        state = PlaceState(),
        onViewFullScreen = {},
        onBackClicked = {},
        onDirect = {}
    )
}