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
import com.sirdave.campusnavigator.domain.model.Place
import com.sirdave.campusnavigator.domain.model.PlaceData
import com.sirdave.campusnavigator.domain.model.places

@Composable
fun DestinationDetail(
    place: Place,
    onViewFullScreen: (PlaceData) -> Unit,
    onBackClicked: () -> Unit,
    onShowMapDirections: () -> Unit,
    onDirect: () -> Unit,
    modifier: Modifier = Modifier
){
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
            itemsIndexed(place.imageUrls){ index, _ ->
                ImageCard(
                    place = place,
                    imageIndex = index,
                    onViewFullScreen = onViewFullScreen,
                )
                Spacer(modifier = modifier.width(8.dp))
            }
        }

        Text(
            text = place.name,
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
                Text(
                    text = "3.5km",
                    modifier = modifier.constrainAs(locationText){
                        bottom.linkTo(parent.bottom)
                        start.linkTo(locationIcon.end)
                    },
                    fontSize = 15.sp
                )
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
                    text = place.placeType,
                    modifier = modifier.constrainAs(directionText){
                        bottom.linkTo(parent.bottom)
                        start.linkTo(directionIcon.end)
                    },
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = modifier.height(16.dp))
        
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            CustomIconButton(
                onClick = onShowMapDirections,
                buttonColor = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.how_to_get_there),
                drawable = R.drawable.baseline_menu
            )

            Spacer(modifier = modifier.width(8.dp))

            CustomIconButton(
                onClick = onDirect,
                buttonColor = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f),
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
        place = places[0],
        onViewFullScreen = {},
        onBackClicked = {},
        onDirect = {},
        onShowMapDirections = {}
    )
}