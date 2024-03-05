package com.sirdave.campusnavigator.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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

@Composable
fun Search(modifier: Modifier = Modifier){
    Column {
        Text(
            text = stringResource(id = R.string.search_label),
            modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search_placeholder))
            },
            label = {
                Text(text = stringResource(id = R.string.search_placeholder))
            },
            modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
        
    }
    
}


@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    Search()
}