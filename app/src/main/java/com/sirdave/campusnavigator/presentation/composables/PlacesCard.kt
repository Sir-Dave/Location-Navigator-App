package com.sirdave.campusnavigator.presentation.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.Place
import com.sirdave.campusnavigator.domain.model.PlaceData
import com.sirdave.campusnavigator.domain.model.places

@Composable
fun PopularPlace(
    place: Place,
    imageIndex: Int,
    onViewFullScreen: (PlaceData) -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .padding(8.dp)
        .width(IntrinsicSize.Min)) {
        ImageCard(
            place = place,
            imageIndex = imageIndex,
            onViewFullScreen = onViewFullScreen
        )

        Text(
            text = place.alias,
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(vertical = 8.dp)
        )

        Text(
            text = place.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Row(modifier = modifier.padding(top = 8.dp)) {
            ConstraintLayout {
                val (locationIcon, locationText) = createRefs()

                Icon(
                    painter = painterResource(id = R.drawable.baseline_location),
                    contentDescription = null,
                    modifier = modifier.constrainAs(locationIcon) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                )
                Text(
                    text = "3.5km",
                    modifier = modifier.constrainAs(locationText) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(locationIcon.end)
                    },
                    fontSize = 15.sp
                )
            }
        }

    }
}

@Composable
fun PlaceCard(
    place: Place,
    imageIndex: Int,
    modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(8.dp)) {
        Row{
            ImageCardSmall(
                place = place,
                imageIndex = imageIndex
            )
            Spacer(modifier = modifier.width(16.dp))
            Column {
                Text(
                    text = place.alias,
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = place.name,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(modifier = modifier.padding(top = 8.dp)) {
                    ConstraintLayout {
                        val (locationIcon, locationText) = createRefs()

                        Icon(
                            painter = painterResource(id = R.drawable.baseline_location),
                            contentDescription = null,
                            modifier = modifier.constrainAs(locationIcon) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                            }
                        )
                        Text(
                            text = "3.5km",
                            modifier = modifier.constrainAs(locationText) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(locationIcon.end)
                            },
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PlacesPreview() {
    PopularPlace(place = places[0], imageIndex = 0, onViewFullScreen = {})
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PlacesCardPreview() {
    PlaceCard(place = places[0], 0)
}