package mx.com.satoritech.satoritickets.utils

import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.utils.Screens.SCREEN_CHAT
import mx.com.satoritech.satoritickets.utils.Screens.SCREEN_TICKETS

object Screens {
    const val SCREEN_SPLASH = "splash"
    const val SCREEN_LOGIN = "login"
    const val SCREEN_MESSAGES = "messages"
    const val SCREEN_CHAT = "chats"
    const val SCREEN_TICKETS = "tickets"
    const val SCREEN_ADD_TICKETS = "add_tickets"
    const val SCREEN_TICKET_PDF = "ticket_pdf"
}

sealed class BottomScreen(
    val title: Int,
    val route: String,
    val icon: Int
){
    object Home: BottomScreen(
        title = R.string.messages,
        route = SCREEN_CHAT,
        icon = R.drawable.ic_chat
    )
    object Ticket: BottomScreen(
        title = R.string.tickets,
        route = SCREEN_TICKETS,
        icon = R.drawable.ic_ticket
    )
}