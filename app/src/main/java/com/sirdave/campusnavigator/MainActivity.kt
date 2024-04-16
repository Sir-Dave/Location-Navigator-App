package com.sirdave.campusnavigator

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sirdave.campusnavigator.domain.model.BottomNavItem
import com.sirdave.campusnavigator.presentation.composables.BottomNavigationBar
import com.sirdave.campusnavigator.presentation.navigation.Navigation
import com.sirdave.campusnavigator.presentation.navigation.Screen
import com.sirdave.campusnavigator.presentation.places.PlaceViewModel
import com.sirdave.campusnavigator.ui.theme.CampusNavigatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel: PlaceViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.getDeviceLocation(fusedLocationProviderClient)
            }
        }

    private fun askPermissions() = when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) -> {
            viewModel.getDeviceLocation(fusedLocationProviderClient)
        }
        else -> {
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        askPermissions()
        val toBeShown = setOf(
            Screen.SearchScreen.route,
            Screen.ExploreScreen.route,
            Screen.UpdatesScreen.route
        )

        setContent {
            CampusNavigatorTheme {
                val navController = rememberNavController()
                val backStackEntry = navController.currentBackStackEntryAsState()
                var showBottomBar by rememberSaveable{
                    mutableStateOf(false)
                }

                showBottomBar = toBeShown.contains(backStackEntry.value?.destination?.route)

                Scaffold(
                    bottomBar = {
                        if (showBottomBar){
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "Search",
                                        route = Screen.SearchScreen.route,
                                        icon = painterResource(id = R.drawable.baseline_location_searching)
                                    ),

                                    BottomNavItem(
                                        name = "Explore",
                                        route = Screen.ExploreScreen.route,
                                        icon = painterResource(id = R.drawable.baseline_explore)
                                    ),

                                    BottomNavItem(
                                        name = "Updates",
                                        route = Screen.UpdatesScreen.route,
                                        icon = painterResource(id = R.drawable.baseline_notifications)
                                    )
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route){
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ){ innerPadding ->
                    Navigation(
                        navHostController = navController,
                        innerPadding,
                        modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

