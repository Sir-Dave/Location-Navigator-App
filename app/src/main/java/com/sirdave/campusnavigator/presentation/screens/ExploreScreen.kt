package com.sirdave.campusnavigator.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.presentation.composables.PopularPlace
import com.sirdave.campusnavigator.presentation.composables.SegmentedPlaces
import com.sirdave.campusnavigator.presentation.places.PlaceEvent
import com.sirdave.campusnavigator.presentation.places.PlaceState

@Composable
fun ExploreScreen(
    state: PlaceState,
    onNavigateToDetails: (String) -> Unit,
    onEvent: (PlaceEvent) -> Unit,
    onViewFullScreen: () -> Unit,
    modifier: Modifier = Modifier,
){
    val places = state.allPlaces

    LaunchedEffect(Unit){
        onEvent(PlaceEvent.GetAllPlaces)
    }

    Column(modifier = modifier.padding(horizontal = 8.dp)) {
        Text(
            text = stringResource(id = R.string.explore),
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search))
            },
            label = {
                Text(text = stringResource(id = R.string.search))
            },
            modifier = modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = modifier.width(8.dp))

        Text(
            text = stringResource(id = R.string.popular_places),
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(vertical = 8.dp)
        )

        LazyRow(modifier = modifier){
            items(places){place ->
                PopularPlace(
                    place = place,
                    imageIndex = 0,
                    onViewFullScreen = onViewFullScreen
                )
            }
        }

        Spacer(modifier = modifier.height(16.dp))

        SegmentedPlaces(
            places = places,
            onNavigateToDetails = onNavigateToDetails
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ExplorePreview() {
    ExploreScreen(
        state = PlaceState(),
        onNavigateToDetails = {},
        onEvent = {},
        onViewFullScreen = {}
    )
}