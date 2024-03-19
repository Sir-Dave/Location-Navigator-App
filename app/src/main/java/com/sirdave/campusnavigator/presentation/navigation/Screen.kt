package com.sirdave.campusnavigator.presentation.navigation

sealed class Screen(val route: String){
    object HomeScreen: Screen("home")
    object SearchScreen: Screen("search")
    object ExploreScreen: Screen("explore")
    object UpdatesScreen: Screen("update")
    object PlacesCategoryListScreen: Screen("places_category_list")

    object DestinationPicturesExpandedScreen: Screen("destination_pictures_expanded")
}