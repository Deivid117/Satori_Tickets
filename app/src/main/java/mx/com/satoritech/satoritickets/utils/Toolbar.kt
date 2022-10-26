package mx.com.satoritech.satoritickets.utils

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.ui.auth.LoginViewModel
import mx.com.satoritech.satoritickets.ui.ticket.TicketItem
import mx.com.satoritech.satoritickets.ui.ticket.TicketViewModel
import mx.com.satoritech.satoritickets.ui.ui.Avenir
import mx.com.satoritech.satoritickets.ui.ui.bottombar
import mx.com.satoritech.satoritickets.ui.ui.shakespeare

@Composable
fun ToolBar(
    user: User? = null,
    loginViewModel: LoginViewModel = hiltViewModel(),
    ticketViewModel: TicketViewModel = hiltViewModel(),
    title: String,
    navController: NavController,
    showLogout: Boolean = false,
    showBottomNav: Boolean = false,
    topAppBarNormal: Boolean = true,
    content: @Composable() () -> Unit = {}
){
    val searchWidgetState by ticketViewModel.searchWidgetState

    Scaffold(
        topBar = {
            MainAppBar(
                navController,
                showLogout,
                topAppBarNormal,
                title,
                loginViewModel,
                ticketViewModel,
                searchWidgetState,
                onCloseClicked = {
                    ticketViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchTriggered = {
                    ticketViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
//            TopBarContent(
//                scaffoldState = if (showLogout) scaffoldState else null,
//                navController = navController,
//                showHeader = showHeader,
//                title = title!!,
//                viewModel = viewModel
//            )
        },
        content = {
            when(searchWidgetState){
                SearchWidgetState.CLOSED -> {
                    content()
                }
                SearchWidgetState.OPENED -> {
                    SearchResults(user, ticketViewModel = ticketViewModel, navController = navController)
                }
            } },
        bottomBar = { if(showBottomNav) BottomNavigationContent(navController) }
    )
}

@Composable
fun TopBarContent(
    title: String = "",
    scaffoldState: ScaffoldState? = null,
    showHeader: Boolean? = false,
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel
){
    val toolbarScope = rememberCoroutineScope()
    Box(modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            Modifier.background(color = shakespeare),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )
            if (showHeader == true) {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                    .background(Color.White)
                    .fillMaxWidth()
                    .height(15.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontFamily = Avenir,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.White
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (scaffoldState != null) {
                IconButton(
                    onClick = {
                        toolbarScope.launch {
                            viewModel.logout()
                            navController.navigate(Screens.SCREEN_LOGIN){
                                popUpTo(0)
                            }
                        } },
                    modifier = Modifier.padding(start = 10.dp)) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(mx.com.satoritech.satoritickets.R.drawable.ic_logout),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            } else if (navController != null) {
                IconButton(
                    onClick = {
                        toolbarScope.launch { navController.popBackStack() }
                              },
                    modifier = Modifier.padding(start = 10.dp)) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(mx.com.satoritech.satoritickets.R.drawable.ic_arrow),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            } else {
                Box() {}
            }
        }
    }
}

@Composable
fun MainAppBar(
    navController: NavController,
    showLogout: Boolean = false,
    topAppBarNormal: Boolean = true,
    title: String,
    loginViewModel: LoginViewModel,
    ticketViewModel: TicketViewModel,
    searchWidgetState: SearchWidgetState,
    onCloseClicked: () -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            TopAppBarContent(
                navController,
                showLogout,
                topAppBarNormal,
                title,
                loginViewModel,
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                navController,
                ticketViewModel,
                onCloseClicked = onCloseClicked,
            )
        }
    }
}

@Composable
fun TopAppBarContent(
    navController: NavController,
    showLogout: Boolean,
    topAppBarNormal: Boolean,
    title: String,
    loginViewModel: LoginViewModel,
    onSearchClicked: () -> Unit
){
    TopAppBar(
        backgroundColor = shakespeare,
    ){
        Box(modifier = Modifier
            .fillMaxWidth()
        ) {
            if (topAppBarNormal) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            if(showLogout){
                                loginViewModel.logout()
                                navController.navigate(Screens.SCREEN_LOGIN){
                                    popUpTo(0)
                                }
                            } else {
                                navController.popBackStack()
                            } },
                        enabled = true,
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = if (showLogout) painterResource(id = R.drawable.ic_logout) else painterResource(
                                id = R.drawable.ic_arrow
                            ),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = title,
                        fontFamily = Avenir,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            } else {
                Box() {
                    Row(
                        Modifier
                            .fillMaxSize()
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { navController.popBackStack() }) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.ic_arrow),
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                        Text(
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            text = title,
                            fontFamily = Avenir,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            onClick = { onSearchClicked() }
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchAppBar(
    navController: NavController,
    ticketViewModel: TicketViewModel,
    onCloseClicked: () -> Unit,
) {

    val textSearch by remember {
        ticketViewModel.searchText
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        //elevation = AppBarDefaults.TopAppBarElevation,
        color = shakespeare
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = textSearch,
            onValueChange = {
                ticketViewModel.setKeyWord(it)
                ticketViewModel.filterContent()
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Búsqueda...",
                    fontFamily = Avenir,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White
                ) },
            textStyle = TextStyle(
                fontFamily = Avenir,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.White
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = { onCloseClicked() }
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = "",
                        tint = Color.White) } },
            trailingIcon = {
                IconButton(
                    onClick = {
                        ticketViewModel.onClearClick()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(15.dp),
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                } },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    ticketViewModel.filterContent()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium),
                unfocusedIndicatorColor = shakespeare,
                focusedIndicatorColor = shakespeare
            )
        )
    }
}

@Composable
fun SearchResults(
    user: User?,
    ticketViewModel: TicketViewModel,
    navController: NavController
){
    val ticketList = ticketViewModel.matchedTickets.value

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(horizontal = 30.dp)
        .padding(bottom = 65.dp)
        ) {
        //Spacer(modifier = Modifier.height(15.dp))
        LazyColumn {
            if(ticketList?.isNotEmpty() == true) {
                ticketList.forEach {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        TicketItem(
                            ticket = it,
                            navController,
                            user = user,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            } else {
                item { NoSearchResults() }
            }
        }
    }
}

@Composable
fun BottomNavigationContent(navController: NavController){

    val screens = listOf(
        BottomScreen.Home,
        BottomScreen.Ticket
    )

    Column(Modifier.padding(start = 10.dp, end = 10.dp, bottom = 5.dp)) {

        BottomNavigation(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(50.dp)),
            backgroundColor = bottombar
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            screens.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = item.icon),
                            contentDescription = ""
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = item.title),
                            fontFamily = Avenir,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                        )
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray,
                    selected = currentRoute == item.route,
                    onClick = { navController.navigate(item.route) }
                )
            }
        }
    }
}