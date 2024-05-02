package com.sirdave.campusnavigator.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.sirdave.campusnavigator.domain.model.PlaceData
import com.sirdave.campusnavigator.presentation.composables.DestinationPictureExpanded
import com.sirdave.campusnavigator.presentation.places.PlaceViewModel
import com.sirdave.campusnavigator.presentation.screens.ExploreScreen
import com.sirdave.campusnavigator.presentation.screens.PlaceCategoryList
import com.sirdave.campusnavigator.presentation.screens.SearchScreen
import com.sirdave.campusnavigator.presentation.screens.UpdatesScreen

private const val PLACE_TYPE = "placeType"
private const val PLACE = "place"

@Composable
fun Navigation(
    navHostController: NavHostController,
    padding: PaddingValues,
    modifier: Modifier = Modifier
){
    val viewModel = hiltViewModel<PlaceViewModel>()
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier
    ){
        navigation(
            route = Screen.HomeScreen.route,
            startDestination = Screen.ExploreScreen.route
        ){
            composable(Screen.ExploreScreen.route){
                ExploreScreen(
                    state = viewModel.placeState,
                    onNavigateToDetails = {
                        val route = Screen.PlacesCategoryListScreen.route + "/$it"
                        navHostController.navigate(route)
                    },
                    onEvent = viewModel::onEvent,
                    onViewPlace = { navHostController.navigate(Screen.SearchScreen.route) },
                    onViewFullScreen = { placeData ->
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(PLACE, placeData)
                        navHostController.navigate(Screen.DestinationPicturesExpandedScreen.route)
                    }
                )
            }

            composable(Screen.SearchScreen.route){
                SearchScreen(
                    state = viewModel.placeState,
                    padding = padding,
                    onEvent = viewModel::onEvent,
                    onViewFullScreen = { place ->
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(PLACE, place)
                        navHostController.navigate(Screen.DestinationPicturesExpandedScreen.route)
                    },
                    onBackClicked = { navHostController.popBackStack() }
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
                    state = viewModel.placeState,
                    title = placeType ?: "",
                    onBackClick = {
                        navHostController.popBackStack()
                    },
                    onEvent = viewModel::onEvent,
                    onViewPlace = { navHostController.navigate(Screen.SearchScreen.route) },
                    onViewFullScreen = { placeData ->
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(PLACE, placeData)
                        navHostController.navigate(Screen.DestinationPicturesExpandedScreen.route)
                    }
                )
            }

            composable(Screen.DestinationPicturesExpandedScreen.route){
                val currentPlace = navHostController.previousBackStackEntry?.savedStateHandle?.get<PlaceData>(PLACE)
                currentPlace?.let { placeData ->
                    DestinationPictureExpanded(
                        selectedPlace = placeData,
                        onBackClicked = { navHostController.popBackStack() }
                    )
                }
            }
        }
    }
}