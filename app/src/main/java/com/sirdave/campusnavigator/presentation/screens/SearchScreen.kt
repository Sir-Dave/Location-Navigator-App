package com.sirdave.campusnavigator.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sirdave.campusnavigator.presentation.composables.Search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(padding: PaddingValues){
    val scaffoldSheetState = rememberBottomSheetScaffoldState()
    val bottomPadding = padding.calculateBottomPadding() + 40.dp

    BottomSheetScaffold(
        scaffoldState = scaffoldSheetState,
        sheetPeekHeight = bottomPadding,
        modifier = Modifier.padding(padding),
        sheetContent = {
            Search()
        },
    ){
        Column(
            modifier = Modifier.padding(it).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Map screen here")
        }
    }
}