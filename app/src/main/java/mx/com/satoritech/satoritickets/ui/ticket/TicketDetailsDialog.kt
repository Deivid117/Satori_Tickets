package mx.com.satoritech.satoritickets.ui.ticket

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.satoritech.domain.models.Ticket
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.domain.models.UserB
import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.utils.DialogLoading
import mx.com.satoritech.satoritickets.utils.Screens
import mx.com.satoritech.satoritickets.ui.chat.ChatViewModel
import mx.com.satoritech.satoritickets.ui.ui.Avenir
import mx.com.satoritech.satoritickets.ui.ui.green
import mx.com.satoritech.satoritickets.ui.ui.orange
import mx.com.satoritech.satoritickets.ui.ui.wattle
import mx.com.satoritech.satoritickets.utils.Constants

// Convertir UserB a User (Extension function)
fun UserB.toUser() = User(
    id = id?:0,
    apiToken = apiToken?:"",
    name = name?:"",
    email = email?:"",
    typeUser = typeUser?:0
)

@Composable
fun TicketDetailDialog(
    ticket: Ticket,
    viewModel: TicketViewModel = hiltViewModel(),
    chatViewModel: ChatViewModel = hiltViewModel(),
    onDemissR: () -> Unit,
    navController: NavController,
    user: User?
){

    val loading by remember{
        viewModel.loading
    }

    if(loading){
        DialogLoading()
    }

    val context = LocalContext.current

    Dialog(onDismissRequest = { onDemissR() }) {
        Card(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            elevation = 10.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.dp)
                        .padding(top = 5.dp, bottom = 15.dp),
                ) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = if((ticket.status?:0) == 0) painterResource(id = R.drawable.ic_pending) else painterResource(id = R.drawable.ic_attended),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(color = if((ticket.status?:0) == 0) orange else green),
                            alignment = Alignment.CenterStart,
                            modifier = Modifier
                                .size(25.dp)
                                .weight(1f)
                        )
                        Image(
                            modifier = Modifier
                                .size(90.dp),
                            painter = painterResource(id = R.drawable.ic_logo_satoritech),
                            contentDescription = "Logo Satoritech",
                            alignment = Alignment.CenterEnd
                        )
                    }
                    Row(Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.date)+": ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = Avenir,
                        )
                        Text(
                            text = ticket.date?:"",
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            fontFamily = Avenir,
                            modifier = Modifier.weight(1f),
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = ticket.team?:"",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = Avenir,
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = ticket.project?:"",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        fontFamily = Avenir,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.content),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontFamily = Avenir,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = ticket.content?:"",
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        fontFamily = Avenir,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                if((user?.typeUser?:0) != 1)
                                    if((ticket.status?:0) == 1){
                                        viewModel.changeStatus(ticket.id?:0, Constants._PENDING, context){ success ->
                                            if(success){ Toast.makeText(context, "Ticket pendiente", Toast.LENGTH_SHORT).show()
                                            chatViewModel.getChat(ticket.creatorId?:0, context, success = {
                                                if(it){
                                                    val chatId = chatViewModel.chatWith.value?.chat?.id
                                                    val userB = (chatViewModel.chatWith.value?.userB)?.toUser()
                                                    val message = "Buen día, quisiera atender el ticket ${ticket.noTicket?:0} del proyecto ${ticket.project?:""}/${ticket.team?:""}"
                                                    navController.currentBackStackEntry?.savedStateHandle?.set<User>("User", userB)
                                                    navController.currentBackStackEntry?.savedStateHandle?.set<String>("Message", message)
                                                    navController.navigate(Screens.SCREEN_MESSAGES+"/"+chatId)
                                                }
                                            })}
                                        }
                                    } else {
                                        chatViewModel.getChat(ticket.creatorId?:0, context, success = {
                                            if(it){
                                                val chatId = chatViewModel.chatWith.value?.chat?.id
                                                val userB =  (chatViewModel.chatWith.value?.userB)?.toUser()
                                                val message = "Buen día, quisiera atender el ticket ${ticket.noTicket?:0} del proyecto ${ticket.project?:""}/${ticket.team?:""}"
                                                navController.currentBackStackEntry?.savedStateHandle?.set<User>("User", userB)
                                                navController.currentBackStackEntry?.savedStateHandle?.set<String>("Message", message)
                                                navController.navigate(Screens.SCREEN_MESSAGES+"/"+chatId)
                                            }
                                        })
                                    }
                                else viewModel.changeStatus(ticket.id?:0, Constants._ATENDEND, context){ success ->
                                    if(success){
                                        Toast.makeText(context, "Ticket atendido", Toast.LENGTH_SHORT).show()
                                        navController.navigate(Screens.SCREEN_TICKETS)
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = wattle),
                            enabled = if ((user?.typeUser?:0) != 1) true else (ticket.status?:0) != 1
                        ) {
                            Text(
                                text =
                                if ((user?.typeUser ?: 0) == 1)
                                    stringResource(id = R.string.ticketAttended)
                                else stringResource(id = R.string.requestTicket),
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                fontFamily = Avenir,
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                val url = "http://192.168.0.6/api/satori/getPdf/${ticket.id ?: 0}"
                                //"https://docs.google.com/gview?embedded=true&url=${APIConstants.getServerPath()}api/satori/getPdf/${ticket.id ?: 0}"

                                startActivity(context ,Intent(Intent.ACTION_VIEW, Uri.parse(url)), bundleOf())

                                //navController.navigate(Screens.SCREEN_TICKET_PDF + "/" + ticket.id)
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                        ) {
                            Text(
                                text = stringResource(id = R.string.seePDF),
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                fontFamily = Avenir,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}