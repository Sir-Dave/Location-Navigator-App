package com.sirdave.campusnavigator.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.sirdave.campusnavigator.domain.model.places
import com.sirdave.campusnavigator.presentation.screens.ExploreScreen
import com.sirdave.campusnavigator.presentation.screens.PlaceCategoryList
import com.sirdave.campusnavigator.presentation.screens.SearchScreen
import com.sirdave.campusnavigator.presentation.screens.UpdatesScreen

private const val PLACE_TYPE = "placeType"

@Composable
fun Navigation(
    navHostController: NavHostController,
    padding: PaddingValues,
    modifier: Modifier = Modifier
){
    val groupedPlaces = places.groupBy { it.placeType }
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
                ExploreScreen(
                    places = places,
                    onNavigateToDetails = {
                        val route = Screen.PlacesCategoryListScreen.route + "/$it"
                        navHostController.navigate(route)
                    }
                )
            }

            composable(Screen.UpdatesScreen.route){
                UpdatesScreen()
            }
            
            composable(
                route = Screen.PlacesCategoryListScreen.route + "/{placeType}",
                arguments = listOf(navArgument(PLACE_TYPE) { type = NavType.StringType })
            ){
                val placeType = it.arguments?.getString(PLACE_TYPE)
                PlaceCategoryList(
                    places = groupedPlaces[placeType] ?: emptyList(),
                    title = placeType ?: "",
                    onBackClick = {
                        navHostController.popBackStack()
                    }
                )
            }
        }
    }
}