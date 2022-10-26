package mx.com.satoritech.satoritickets.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import mx.com.satoritech.web.Token
import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.utils.Screens
import mx.com.satoritech.satoritickets.ui.ui.pictonBlue

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val user = mutableStateOf(false)

    viewModel.user.observe(LocalLifecycleOwner.current) {
        it?.apiToken?.let { token ->
            user.value = true
            Token.token = token
        }
    }

    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.popBackStack()
            if (user.value) {
                navController.navigate(Screens.SCREEN_CHAT)
            } else {
                navController.navigate(Screens.SCREEN_LOGIN)
            }
    }

    Box(modifier = Modifier.fillMaxSize().background(color = pictonBlue)) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}