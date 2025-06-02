package com.pablosuero.apppablo.ui.navigation

import ListViewModel
import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pablosuero.apppablo.data.model.AuthManager
import com.pablosuero.apppablo.ui.screen.ListScreen.ListScreen
import com.pablosuero.apppablo.ui.screen.DetailScreen.DetailScreen
import com.pablosuero.apppablo.ui.screen.forgotPasswordScreen.ForgotPasswordScreen
import com.pablosuero.apppablo.ui.screen.loginScreen.LoginScreen
import com.pablosuero.apppablo.ui.screen.signUpScreen.SignUpScreen

@Composable
fun AppNavigation(auth: AuthManager) {
    val navController = rememberNavController()
//    val context = LocalContext.current
    val listViewModel: ListViewModel = viewModel()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            LoginScreen(
                auth,
                { navController.navigate(SignUp) },
                { navController.navigate(List) },
                { navController.navigate(ForgotPassword) }
            )
        }

        composable<SignUp> {
            SignUpScreen(
                auth
            ) { navController.popBackStack() }
        }

        composable<ForgotPassword> {
            ForgotPasswordScreen(
                auth
            ) {
                navController.navigate(Home) {
                    popUpTo(Home) { inclusive = true }
                }
            }
        }

        composable<List> {
            ListScreen(
                listViewModel = listViewModel,
                navigateToDetail = { mealId ->
                    navController.navigate(Detail(mealId))
                },
                onBack = { navController.popBackStack() },
            )
        }

        composable<Detail> { backStackEntry ->
            val detail = backStackEntry.toRoute<Detail>()
            val mealId = detail.id
            DetailScreen(
                mealId = mealId,
                onBack = { navController.popBackStack() },
                listViewModel = listViewModel
            )
        }
    }
}
