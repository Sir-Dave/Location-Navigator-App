package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.presentation.places.PlaceEvent
import com.sirdave.campusnavigator.presentation.places.PlaceState

@Composable
fun Search(
    state: PlaceState,
    onEvent: (PlaceEvent) -> Unit,
    modifier: Modifier = Modifier
){
    Column {
        Text(
            text = stringResource(id = R.string.search_label),
            modifier = modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { onEvent(PlaceEvent.OnSearchQueryChanged(it)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search_placeholder))
            },
            label = {
                Text(text = stringResource(id = R.string.search_placeholder))
            },
            modifier = modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = modifier.height(50.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(state.allPlaces){ place ->
                SearchPlaceItem(
                    place = place,
                    onViewDetails = {}
                )
            }
        }
        
    }
    
}


@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    Search(
        state = PlaceState(),
        onEvent = {}
    )
}