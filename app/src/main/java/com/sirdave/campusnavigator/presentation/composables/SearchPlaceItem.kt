package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.domain.model.Place
import com.sirdave.campusnavigator.domain.model.places

@Composable
fun SearchPlaceItem(
    place: Place,
    onViewDetails: (Place) -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .padding(8.dp)
        .fillMaxWidth()
    ) {
        Row(modifier = modifier.clickable { onViewDetails(place) }){
            Icon(
                painter = painterResource(id = R.drawable.baseline_location),
                contentDescription = null,
                modifier = modifier
            )

            Spacer(modifier = modifier.width(8.dp))

            Column(modifier = modifier) {
                Text(
                    text = place.alias,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )

                Text(
                    text = place.name,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun SearchPlacePreview() {
    SearchPlaceItem(
        place = places[0],
        onViewDetails = {}
    )
}