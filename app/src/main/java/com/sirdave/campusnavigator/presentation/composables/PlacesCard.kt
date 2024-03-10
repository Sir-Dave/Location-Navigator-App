package com.sirdave.campusnavigator.presentation.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.Place
import com.sirdave.campusnavigator.domain.model.PlaceType
import java.time.LocalDateTime

val place = Place(
    id = 8,
    name = "Obafemi Awolowo Stadium, University of Ibadan",
    alias = "Awo Stadium",
    longitude = 0.0,
    latitude = 0.0,
    placeType = PlaceType.SPORT_FACILITY.title,
    createdAt = LocalDateTime.now(),
    imageUrls = mutableListOf(""),
    category = null,
    updatedAt = null
)
@Composable
fun PopularPlace(
    place: Place,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .padding(8.dp)
        .width(IntrinsicSize.Min)) {
        ImageCard(place = place)

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
    modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(8.dp)) {
        Row{
            ImageCardSmall(place = place)
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


@Composable
fun SegmentedPlace(
    places: List<Place>,
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val groupedPlaces = places.groupBy { it.placeType }
    groupedPlaces.forEach{(title, places) ->
        Column(modifier = modifier.padding(8.dp)) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(vertical = 16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(id = R.string.see_all),
                    modifier = modifier.clickable {
                        onNavigateToDetails(title)
                    }
                )
            }

            LazyColumn(modifier = modifier){
                items(places){ place ->
                    PlaceCard(place = place)

                }
            }
        }

    }
    /*Column(modifier = modifier.padding(8.dp)) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(vertical = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(id = R.string.see_all),
                modifier = modifier.clickable {
                    onNavigateToDetails()
                }
            )
        }

        LazyColumn(modifier = modifier){
            items(5){
                PlaceCard()
            }
        }
    }*/
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PlacesPreview() {
    PopularPlace(place = place)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PlacesCardPreview() {
    PlaceCard(place = place)
}