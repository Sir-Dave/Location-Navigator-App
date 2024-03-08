package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

@Composable
fun PopularPlace(modifier: Modifier = Modifier){
    Column(modifier = modifier
        .padding(8.dp)
        .width(IntrinsicSize.Min)) {
        ImageCard()

        Text(
            text = stringResource(id = R.string.placeholder_place_alias),
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(vertical = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.placeholder_place_name),
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
fun PlaceCard(modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(8.dp)) {
        Row{
            ImageCardSmall()
            Spacer(modifier = modifier.width(16.dp))
            Column {
                Text(
                    text = stringResource(id = R.string.placeholder_place_alias),
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = stringResource(id = R.string.placeholder_place_name),
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
    title: String,
    onNavigateToDetails: () -> Unit,
    modifier: Modifier = Modifier
){
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
                    onNavigateToDetails()
                }
            )
        }

        LazyColumn(modifier = modifier){
            items(5){
                PlaceCard()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PlacesPreview() {
    PopularPlace()
}

@Preview(showBackground = true)
@Composable
fun PlacesCardPreview() {
    PlaceCard()
}