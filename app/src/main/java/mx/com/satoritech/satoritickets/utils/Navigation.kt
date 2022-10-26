package mx.com.satoritech.satoritickets.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.satoritickets.ui.auth.LoginScreen
import mx.com.satoritech.satoritickets.ui.chat.ChatScreen
import mx.com.satoritech.satoritickets.ui.message.MessageScreen
import mx.com.satoritech.satoritickets.ui.ticket.AddTicketScreen
import mx.com.satoritech.satoritickets.ui.ticket.TicketPdf
import mx.com.satoritech.satoritickets.ui.ticket.TicketScreen
import mx.com.satoritech.satoritickets.ui.splash.SplashScreen

@Composable
fun Navigation(navController: NavController = rememberNavController()) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.SCREEN_SPLASH,
    ){
        composable(Screens.SCREEN_SPLASH) { SplashScreen(navController) }

        composable(Screens.SCREEN_LOGIN){ LoginScreen(navController) }

        composable(Screens.SCREEN_CHAT){ ChatScreen(navController) }

        composable(Screens.SCREEN_MESSAGES+"/{chat}"){ backStackEntry ->
            val userB = navController.previousBackStackEntry?.savedStateHandle?.get<User>("User")
            val message = navController.previousBackStackEntry?.savedStateHandle?.get<String>("Message")
            backStackEntry.arguments?.getString("chat")?.let {
                if(userB != null || message != null) {
                    MessageScreen(navController, it, userB, message)
                }
            }
        }
        composable(Screens.SCREEN_TICKETS){ TicketScreen(navController) }

        composable(Screens.SCREEN_ADD_TICKETS){ AddTicketScreen(navController) }

        composable(Screens.SCREEN_TICKET_PDF + "/{ticket_id}"){ backStackEntry ->
            backStackEntry.arguments?.getString("ticket_id")?.let {
                TicketPdf(navController, it)
            }
        }
    }
}

