package com.sirdave.campusnavigator.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sirdave.campusnavigator.presentation.screens.ExploreScreen
import com.sirdave.campusnavigator.presentation.screens.SearchScreen
import com.sirdave.campusnavigator.presentation.screens.UpdatesScreen

@Composable
fun Navigation(
    navHostController: NavHostController,
    padding: PaddingValues,
    modifier: Modifier = Modifier
){

    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier
    ){
        navigation(
            route = Screen.HomeScreen.route,
            startDestination = Screen.SearchScreen.route
        ){
            composable(Screen.SearchScreen.route){
                SearchScreen(padding)
            }

            composable(Screen.ExploreScreen.route){
                ExploreScreen()
            }

            composable(Screen.UpdatesScreen.route){
                UpdatesScreen()
            }
        }
    }
}