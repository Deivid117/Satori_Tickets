package mx.com.satoritech.satoritickets.ui.message

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import mx.com.satoritech.domain.models.Message
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.ui.auth.LoginViewModel
import mx.com.satoritech.satoritickets.ui.ui.*
import mx.com.satoritech.satoritickets.utils.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MessageScreen(
    navController: NavController,
    chatId: String,
    userB: User?,
    message: String?,
    viewModel: MessageViewModel = hiltViewModel(),
    userViewModel: LoginViewModel = hiltViewModel(),
) {
    val userA by userViewModel.user.observeAsState()
    val context = LocalContext.current

    val isLoading by remember {
        viewModel.isLoading
    }

    if((userA != null) && ((userA?.id ?: 0) != 0)){
        LaunchedEffect(viewModel){
            viewModel.getMessages(chatId.toLong(),context)
            viewModel.init((userA?.id).toString())
        }
    }

    ToolBar(
        navController = navController,
        showLogout = false,
        title = userB?.name?:"",
    ) {
        if(isLoading) {
            LottieLoadingCircle()
        } else {
            MessagesList(viewModel, userA)
        }
        SendMessage(viewModel, userA, userB, chatId, message)
    }
}

@Composable
fun SendMessage(
    viewModel: MessageViewModel,
    userA: User?,
    userB: User?,
    chatId: String,
    message: String?
){
    val context = LocalContext.current

    var content by remember { mutableStateOf(message) }

    val date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDateTime.now())
    val time = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now())

    viewModel.setSenderId(userA?.id?.toString()?:"")
    viewModel.setReceiverId(userB?.id?.toString()?:"")
    viewModel.setChatId(chatId)
    viewModel.setMessageDate(date)
    viewModel.setMessageHour(time)
    viewModel.setContent(content?:"")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        Box() {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(messageItem)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 5.dp, bottom = 5.dp)
                        .heightIn(1.dp, 120.dp),
                    shape = RoundedCornerShape(30.dp),
                    value = content ?: "",
                    onValueChange = { content = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.sendMessage),
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            fontFamily = Avenir,
                            color = messagePlaceHolder
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = shakespeare,
                        unfocusedBorderColor = Color.White,
                        focusedBorderColor = Color.White,
                    ),
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        fontFamily = Avenir,
                    ),
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier
                    .size(45.dp)
                    .clip(shape = CircleShape)
                    .background(color = wattle)
                    .clickable {
                        viewModel.sentMessage(context) {
                            if (it) {
                                content = ""
                                viewModel.getMessages(chatId.toLong(), context)
                            }
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .align(alignment = Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun MessagesList(
    viewModel: MessageViewModel,
    userA: User?
){
    val messagesList = mutableStateOf<List<Message>>(listOf())

    val owner = LocalLifecycleOwner.current
    viewModel.listMessages.observe(owner, Observer { list ->
        messagesList.value = list
    })

    Column(
        Modifier
            .fillMaxWidth() // 78.dp
            .padding(start = 10.dp, end = 10.dp, bottom = 78.dp)
    ) {
        LazyColumn(reverseLayout = true) {
            if (messagesList.value.isNotEmpty()) {
                items(messagesList.value.reversed()) { item ->
                    MessageItem(item, userA)
                }
            } else {
                item { EmpyData() }
            }
        }
    }
}

@Composable
fun MessageItem(
    message: Message,
    userA: User?
){
    if((message.senderId ?: 0) == (userA?.id ?: 0)){
        ItemRigth(message.content?:"", message.messageHour?.substring(0..4)?:"")
    } else {
        ItemLeft(message.content?:"", message.messageHour?.substring(0..4)?:"")
    }
}
