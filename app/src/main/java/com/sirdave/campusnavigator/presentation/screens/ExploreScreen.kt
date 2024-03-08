package com.sirdave.campusnavigator.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.presentation.composables.PopularPlace
import com.sirdave.campusnavigator.presentation.composables.SegmentedPlace

@Composable
fun ExploreScreen(
    onNavigateToDetails: () -> Unit,
    modifier: Modifier = Modifier,
){
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
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = modifier.width(8.dp))

        Text(
            text = stringResource(id = R.string.popular_places),
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        )

        LazyRow(modifier = modifier){
            items(5){
                PopularPlace()
            }
        }

        Spacer(modifier = modifier.width(16.dp))

        SegmentedPlace(
            title = stringResource(id = R.string.halls_of_residence),
            onNavigateToDetails = onNavigateToDetails
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ExplorePreview() {
    ExploreScreen(
        onNavigateToDetails = {}
    )
}