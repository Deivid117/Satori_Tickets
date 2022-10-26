package mx.com.satoritech.satoritickets.ui.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.ui.ui.Avenir
import mx.com.satoritech.satoritickets.ui.ui.messageText
import mx.com.satoritech.domain.models.User
import androidx.lifecycle.Observer
import kotlinx.coroutines.flow.merge
import mx.com.satoritech.domain.models.Message
import mx.com.satoritech.domain.models.UserChat
import mx.com.satoritech.satoritickets.ui.auth.LoginViewModel
import mx.com.satoritech.satoritickets.ui.ui.green
import androidx.compose.ui.graphics.Color
import mx.com.satoritech.satoritickets.utils.*

@Composable
fun ChatScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
){
    val userA by loginViewModel.user.observeAsState()

    val context = LocalContext.current

    val isLoading by remember {
        viewModel.isLoading
    }

    LaunchedEffect(viewModel){
        viewModel.getListChats(context)
    }

    ToolBar(
        navController = navController,
        showLogout = true,
        title = stringResource(id = R.string.chat),
        showBottomNav = true
    ) {
        Surface(
            Modifier.fillMaxSize(),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 65.dp)
            ) {
                if(isLoading){
                    ShimmerGridLoad(9)
                } else {
                    ChatList(navController, viewModel, userA)
                }
            }
        }
    }
}

@Composable
fun ChatList(
    navController: NavController,
    viewModel: ChatViewModel,
    userA: User?
){
    val listMessage = viewModel.listChat.value?.messages
    val listUsers = viewModel.listChat.value?.user

    LazyColumn {
        if (listMessage?.isNotEmpty() == true && listUsers?.isNotEmpty() == true) {
            items(listUsers) { userB ->
                ChatItem(navController, userB, userA, listMessage, viewModel)
            }
        } else {
            item { EmpyData() }
        }
    }
}

@Composable
fun ChatItem(
    navController: NavController,
    userB: User?,
    userA: User?,
    message: List<Message?>,
    viewModel: ChatViewModel
) {
    val context = LocalContext.current
    var messageContent = ""
    var messageHour = ""
    var messageReceiverId = 0
    var messageSeenMessage = 0

    message.forEach { message ->
        if( ((message?.senderId ?: 0) == (userB?.id ?: 0) || (message?.senderId?:0) == (userA?.id?:0))
            && ((message?.receiverId?:0) == (userA?.id?:0) || (message?.receiverId?:0) == (userB?.id?:0))){
            messageContent = message?.content?:""
            messageHour = message?.messageHour?.substring(0..4)?:""
            messageReceiverId = message?.receiverId?:0
            messageSeenMessage = message?.seenMessage?:0
        }
    }

    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .clickable {
                viewModel.getChat(userB?.id ?: 0, context,
                    success = {
                        if (it) {
                            val chatId = viewModel.chatWith.value?.chat?.id
                            navController.currentBackStackEntry?.savedStateHandle?.set<User>(
                                "User",
                                userB
                            )
                            navController.navigate(Screens.SCREEN_MESSAGES + "/" + chatId)
                        }
                    }
                )
            },
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape
            ) {
                Image(
                    painter = painterResource(id = R.drawable.satori_logo),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                Modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f)
            ) {
                Text(
                    text = userB?.name?:"",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Avenir,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = messageContent,
                    color = if(((userA?.id?:0) == messageReceiverId) && messageSeenMessage == 0) { Color.Black } else { messageText },
                    fontSize = 15.sp,
                    fontWeight = if(((userA?.id?:0) == messageReceiverId) && messageSeenMessage == 0) { FontWeight.SemiBold } else { FontWeight.Normal },
                    fontFamily = Avenir,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                //horizontalArrangement = Arrangement.End,
                horizontalAlignment = Alignment.End,
               // modifier = Modifier
                 //   .fillMaxWidth()
                    //.weight(1f)
            ) {
                Text(
                    text = messageHour,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Avenir,
                )
                Spacer(modifier = Modifier.height(6.dp))
                if(((userA?.id?:0) == messageReceiverId) && messageSeenMessage == 0) {
                    Box(
                        Modifier
                            .background(
                                color = green,
                                shape = CircleShape
                            )
                            .size(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "!",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Avenir,
                        )
                    }
                }
            }
        }
    }
}
