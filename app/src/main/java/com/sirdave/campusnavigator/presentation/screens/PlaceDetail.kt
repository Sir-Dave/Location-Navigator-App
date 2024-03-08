package com.sirdave.campusnavigator.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sirdave.campusnavigator.R
import com.sirdave.campusnavigator.presentation.composables.PlaceCard

@Composable
fun PlaceDetail(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Column {

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = modifier.padding(vertical = 16.dp).clickable { onBackClick() }
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )

        LazyColumn(modifier = modifier){
            items(10){
                PlaceCard()
            }
        }
        
    }

}

@Preview(showBackground = true)
@Composable
fun PlaceDetailPreview(){
    PlaceDetail(
        stringResource(id = R.string.halls_of_residence),
        onBackClick = {}
    )
}