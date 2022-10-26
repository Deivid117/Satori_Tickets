package mx.com.satoritech.satoritickets.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.ui.ui.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import mx.com.satoritech.satoritickets.utils.LottieLoadingDots
import mx.com.satoritech.satoritickets.utils.Screens

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
){
    Surface(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        color = downy
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                Modifier
                    .fillMaxWidth()) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.fondo_app),
                    contentDescription = "App logo",
                    contentScale = ContentScale.Crop,
                )
                Image(
                    painter = painterResource(id = R.drawable.logo_app),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 15.dp)
                        .size(130.dp)
                )
            }
        }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                Modifier
                    .height(360.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 34.dp),
                shape = RoundedCornerShape(5.dp),
                elevation = 20.dp
            ) {
                LoginForm(navController, viewModel)
            }
        }
    }
}

@Composable
fun LoginForm(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val isLoading by remember { viewModel.isLoading }

    var error = ""
    var errorPassword = ""
    val context = LocalContext.current

    val email by remember { viewModel.email }
    val emailError by remember { viewModel.emailError }
    val emailErrorBoolean by remember { viewModel.emailErrorBoolean }

    val password by remember { viewModel.password }
    val passwordError by remember { viewModel.passwordError }
    val passwordErrorBoolean by remember { viewModel.passwordErrorBoolean }

    val focusManager = LocalFocusManager.current

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 35.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = {
                viewModel.setEmail(it)
                viewModel.setErrorEmail(false)
                            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = shakespeare,
                focusedIndicatorColor = shakespeare
            ),
            textStyle = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                fontFamily = Avenir,
                color = Color.Black),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.email),
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    fontFamily = Avenir,
                    color = Color.Black
                )},
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_login),
                    contentDescription = "",
                    tint = Color.Gray,
                )
            },
            singleLine = true,
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            isError = emailErrorBoolean
        )
        if(emailErrorBoolean)
            error = if(emailError != 0) stringResource(id = emailError) else ""

        Text(
            text = error,
            fontFamily = Avenir,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.Red,
            modifier = Modifier.padding(start = 15.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                viewModel.setPassword(it)
                viewModel.setErrorPassword(false)
                            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = shakespeare,
                focusedIndicatorColor = shakespeare
            ),
            textStyle = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                fontFamily = Avenir,
                color = Color.Black),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.password),
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    fontFamily = Avenir,
                    color = Color.Black
                )},
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = "",
                    tint = Color.Gray,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = if (isPasswordVisible) painterResource(id = R.drawable.ic_visibility_off) else painterResource(id = R.drawable.ic_visibility),
                        contentDescription = "",
                        tint = Color.Gray,
                    )
                }
            },
            isError = passwordErrorBoolean
        )
        if(passwordErrorBoolean)
            errorPassword = if(passwordError != 0) stringResource(id = passwordError) else ""

        Text(
            text = errorPassword,
            fontFamily = Avenir,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.Red,
            modifier = Modifier.padding(start = 15.dp)
        )
        if (isLoading) {
            LottieLoadingDots()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Card(elevation = 10.dp, modifier = Modifier.padding(bottom = 35.dp)) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(2.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = wattle),
                onClick = {
                    viewModel.login({
                        navController.navigate(Screens.SCREEN_CHAT){
                            popUpTo(0)
                            this.launchSingleTop = true
                        }
                    }, context)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    fontFamily = Avenir,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    LoginScreen(rememberNavController())
}