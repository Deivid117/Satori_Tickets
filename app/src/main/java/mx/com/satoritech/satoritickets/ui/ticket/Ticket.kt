package mx.com.satoritech.satoritickets.ui.ticket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import mx.com.satoritech.domain.models.Ticket
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.ui.auth.LoginViewModel
import mx.com.satoritech.satoritickets.ui.ui.*
import mx.com.satoritech.satoritickets.utils.*

@Composable
fun TicketScreen(
    navController: NavController,
    viewModel: TicketViewModel = hiltViewModel(),
    userViewModel: LoginViewModel = hiltViewModel()
){
    val user by userViewModel.user.observeAsState()
    val searchWidgetState by viewModel.searchWidgetState
    var showBottomNav by remember { mutableStateOf(true) }

    ToolBar(
        user,
        navController = navController,
        showLogout = false,
        title = stringResource(id = R.string.tickets),
        showBottomNav = showBottomNav,
        topAppBarNormal = false,
    ) {
        when(searchWidgetState){
            SearchWidgetState.OPENED -> {
                showBottomNav = false
            }
        }
        TabRowTicket(viewModel, navController, user)

        if((user?.typeUser ?: 0) == 1){
            ButtonAddTicket(navController)
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TabRowTicket(
    viewModel: TicketViewModel,
    navController: NavController,
    user: User?
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(shakespeare)
        .height(20.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        val context = LocalContext.current
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        val pages = listOf(
            "Todos",
            "Atendidos",
            "Pendientes"
        )

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    height = 4.dp,
                    color = wattle
                )
            },
            backgroundColor = Color.White,
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = Avenir,
                        ) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        when (pagerState.currentPage) {
            0 -> {
                viewModel.getTickets(Constants.ALL, context)
            }
            1 -> {
                viewModel.getTickets(Constants.ATENDED, context)
            }
            2 -> {
                viewModel.getTickets(Constants.PENDING, context)
            }
        }

        HorizontalPager(
            count = pages.size,
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> TicketPage( viewModel, navController, user)
                1 -> TicketPage(viewModel, navController, user)
                2 -> TicketPage(viewModel, navController, user)
            }
        }
    }
}

@Composable
fun ButtonAddTicket(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column() {
            Row() {
                Box(
                    modifier = Modifier
                        .size(59.dp)
                        .clip(shape = CircleShape)
                        .background(color = pictonBlue)
                        .clickable { navController.navigate(Screens.SCREEN_ADD_TICKETS) }
                ) {
                    Image(
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                            .size(28.dp),
                        painter = painterResource(id = R.drawable.ic_add),
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = ""
                    )
                }
                Spacer(modifier = Modifier.width(30.dp))
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun TicketPage(
    viewModel: TicketViewModel,
    navController: NavController,
    user: User?
) {
    val isLoading by remember {
        viewModel.loading
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, bottom = 65.dp)
    ) {
        if (isLoading) {
            CardShimmerGridLoad(total = 5)
        } else {
            TicketList(navController, viewModel, user)
        }
    }
}

@Composable
fun TicketList(
    navController: NavController,
    viewModel: TicketViewModel,
    user: User?
){
    val listTicket = mutableStateOf<List<Ticket>>(listOf())

    val owner = LocalLifecycleOwner.current
    viewModel.listTickets.observe(owner, Observer { list ->
        listTicket.value = list
    })

    LazyColumn {
        if (listTicket.value.isNotEmpty()) {
            items(listTicket.value.reversed()) { item ->
                Spacer(modifier = Modifier.height(10.dp))
                TicketItem(item, navController, user)
                Spacer(modifier = Modifier.height(10.dp))
            }
        } else {
            item { EmpyData() }
        }
    }
}

@Composable
fun TicketItem(
    ticket: Ticket,
    navController: NavController,
    user: User?
){
    var showDialog by remember{ mutableStateOf(false)}
    if(showDialog) {
        TicketDetailDialog(
            onDemissR = {
                showDialog = false },
            ticket = ticket,
            navController = navController,
            user = user
        )
    }

    Card(
        elevation = 15.dp,
        shape = CutCornerShape(10),
        backgroundColor = springRain,
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { showDialog = true },
    ) {
        Column(
            Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .border(.5.dp, color = Color.Black, shape = CutCornerShape(10))
        ) {
            Column(
                Modifier
                    .padding(horizontal = 15.dp, vertical = 15.dp)
            ) {
                Row() {
                    Text(
                        text = "Ticket No. ${ticket.noTicket?:0}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Avenir,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = ticket.date?:"",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = Avenir,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = ticket.content?:"",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Avenir,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}



