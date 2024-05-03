package com.sirdave.campusnavigator.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.PlaceData
import com.sirdave.campusnavigator.presentation.composables.PlaceCard
import com.sirdave.campusnavigator.presentation.places.PlaceEvent
import com.sirdave.campusnavigator.presentation.places.PlaceState

@Composable
fun PlaceCategoryList(
    state: PlaceState,
    title: String,
    onBackClick: () -> Unit,
    onEvent: (PlaceEvent) -> Unit,
    onViewPlace: () -> Unit,
    onViewFullScreen: (PlaceData) -> Unit,
    modifier: Modifier = Modifier) {

    val places = state.allPlaces

    LaunchedEffect(Unit){
        onEvent(PlaceEvent.SearchPlacesByType(title))
    }

    Column {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = modifier
                .padding(vertical = 16.dp)
                .clickable { onBackClick() }
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )

        LazyColumn(modifier = modifier){
            itemsIndexed(places){index, place ->
                PlaceCard(
                    place = place,
                    imageIndex = 0,
                    onSelectPlace = {
                        onEvent(PlaceEvent.OnPlaceSelected(place))
                        onViewPlace()
                    },
                    onViewFullScreen = {
                        val placeData = PlaceData(place, index)
                        onViewFullScreen(placeData)
                    }
                )
            }
        }
        
    }

}

@Preview(showBackground = true)
@Composable
fun PlaceDetailPreview(){
    PlaceCategoryList(
        state = PlaceState(),
        title = stringResource(id = R.string.halls_of_residence),
        onEvent = {},
        onBackClick = {},
        onViewFullScreen = {},
        onViewPlace = {}
    )
}