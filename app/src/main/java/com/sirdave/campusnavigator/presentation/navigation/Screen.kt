package com.sirdave.campusnavigator.presentation.navigation

sealed class Screen(val route: String){
    object HomeScreen: Screen("home")
    object SearchScreen: Screen("search")
    object ExploreScreen: Screen("explore")
    object UpdatesScreen: Screen("update")
    object PlaceDetailScreen: Screen("place_detail")
}