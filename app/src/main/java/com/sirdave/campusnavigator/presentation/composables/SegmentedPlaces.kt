package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.Place

@Composable
fun SegmentedPlaces(
    places: List<Place>,
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 20.dp)
    ){
        val groupedPlaces = places.groupBy { it.placeType }
        groupedPlaces.forEach{(title, places) ->
            item {
                PlaceHeader(
                    title = title,
                    onNavigateToDetails = onNavigateToDetails
                )
            }
            items(places){ place ->
                PlaceCard(
                    place = place,
                    imageIndex = 0
                )
            }
        }
    }
}

@Composable
fun PlaceHeader(
    title: String,
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
){
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
}