package com.trodar.paging3compose.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.trodar.paging3compose.screen.DetailScreen
import com.trodar.paging3compose.screen.MovieListScreen
import kotlinx.serialization.Serializable

@Serializable
object List

@Serializable
data class Detail(val imdbId: String)

@Composable
fun MovieNavHost(
    paddingValues: PaddingValues,
) {
    val navController: NavHostController = rememberNavController()
    NavHost(navController, startDestination = List) {
        composable<List> {
            MovieListScreen(
                paddingValues = paddingValues,
                onNavigateToDetail = { imdbId ->
                    navController.navigate(Detail(imdbId))
                }
            )
        }
        composable<Detail> { backStackEntry ->
            val detail: Detail = backStackEntry.toRoute()
            DetailScreen(
                imdbId = detail.imdbId,
                paddingValues = paddingValues,
                onBack = navController::popBackStack
            )
        }
    }
}