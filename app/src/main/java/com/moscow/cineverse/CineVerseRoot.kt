package com.moscow.cineverse

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.moscow.cineverse.component.bottomNavigationBar.BottomNavItem.Companion.destinations
import com.moscow.cineverse.component.bottomNavigationBar.NavBar
import com.moscow.cineverse.navigation.CineVerseNavGraph
import com.moscow.cineverse.navigation.NavViewModel
import com.moscow.cineverse.navigation.navigateToNewGraph
import com.moscow.cineverse.navigation.rememberIsInGraph
import com.moscow.cineverse.navigation.rememberNavGraphIndex

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CineVerseRoot(
    navViewModel: NavViewModel
) {

    val navController = rememberNavController()

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()

    val navGraphIndex by rememberNavGraphIndex(currentNavBackStackEntry, destinations.keys)

    val isNavBarVisible by rememberIsInGraph(currentNavBackStackEntry, destinations.keys)

    var matchBottomNavVisible by rememberSaveable { mutableStateOf(true) }

    val showBottomNav = isNavBarVisible && matchBottomNavVisible

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        bottomBar = {
            if (showBottomNav) NavBar(
                selectedItem = destinations.values.elementAt(navGraphIndex),
                onItemClick = { index, _ ->
                    val targetGraph = destinations.keys.elementAt(index)
                    navController.navigateToNewGraph(targetGraph)
                })
        }
    ) { paddingValues ->
        CineVerseNavGraph(
            modifier = Modifier
                .padding(top = 16.dp)
                .navigationBarsPadding(),
            navController = navController,
            navViewModel = navViewModel,
            scaffoldPaddingValues = paddingValues,
            onBottomNavVisibilityChange = { isVisible ->
                matchBottomNavVisible = isVisible
            }
        )
    }
}
