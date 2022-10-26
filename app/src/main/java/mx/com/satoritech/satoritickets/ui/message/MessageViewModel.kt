package mx.com.satoritech.satoritickets.ui.message

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.com.satoritech.domain.models.Message
import mx.com.satoritech.domain.models.MessageSocket
import mx.com.satoritech.satoritickets.repository.LocalUserRepository
import mx.com.satoritech.satoritickets.repository.MessageRepository
import mx.com.satoritech.satoritickets.utils.Constants
import mx.com.satoritech.web.NetworkResult
import mx.com.satoritech.web.websocket.MessageListener
import mx.com.satoritech.web.websocket.WebSocketManager
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    val messageRepository: MessageRepository,
    application: Application
): AndroidViewModel(application), MessageListener {

    val serverUrl = "ws://192.168.0.6:5002"
    val messageListener = this

    private val _isLoading = mutableStateOf(false)
    var isLoading = _isLoading

    private val _content = mutableStateOf("")
    private val _senderId = mutableStateOf("")
    private val _receiverId = mutableStateOf("")
    private val _chatId = mutableStateOf("")
    private val _messageDate = mutableStateOf("")
    private val _messageHour = mutableStateOf("")

    val content: State<String> = _content
    /*val userId: State<String> = _userId
    val chatId: State<String> = _chatId
    val messageDate: State<String> = _messageDate
    val messageHour: State<String> = _messageHour*/

    fun setContent(content: String) {
        _content.value = content
    }
    fun setSenderId(senderId: String) {
        _senderId.value = senderId
    }
    fun setReceiverId(receiverId: String) {
        _receiverId.value = receiverId
    }
    fun setChatId(chatId: String) {
        _chatId.value = chatId
    }
    fun setMessageDate(messageDate: String) {
        _messageDate.value = messageDate
    }
    fun setMessageHour(messageHour: String) {
        _messageHour.value = messageHour
    }

    fun validate(): Boolean {
        val content = this.content.value
        var isValid = true

        if (content.isEmpty()) {
            isValid = false
        }
        return isValid
    }

    fun sentMessage(context: Context, success: (Boolean) -> Unit) = viewModelScope.launch{
        if(validate()) {
            messageRepository.sentMessage(
                Message(
                    content = _content.value,
                    seenMessage = Constants.SEEN_MESSAGE,
                    chatId = _chatId.value.toInt(),
                    senderId = _senderId.value.toInt(),
                    receiverId = _receiverId.value.toInt(),
                    messageDate = _messageDate.value,
                    messageHour = _messageHour.value
                )
            ).collect() {
                when (it) {
                    is NetworkResult.Success -> {
                        val message = Message(
                            content = _content.value,
                            seenMessage = Constants.SEEN_MESSAGE,
                            chatId = _chatId.value.toInt(),
                            senderId = _senderId.value.toInt(),
                            receiverId = _receiverId.value.toInt(),
                            messageDate = _messageDate.value,
                            messageHour = _messageHour.value
                        )
                        WebSocketManager.sendMessage(message.toJson())
                        _isLoading.value = false
                        success(true)
                    }
                    is NetworkResult.Error -> {
                        _isLoading.value = false
                        Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG).show()
                        success(false)
                    }
                    is NetworkResult.Loading -> {
                        _isLoading.value = true
                        success(false)
                    }
                }
            }
        }
    }

    private val _listMessages = MutableLiveData<List<Message>>(listOf())
    val listMessages: LiveData<List<Message>> = _listMessages

    fun setOnListMessage(it: List<Message>){

    }

    fun getMessages(id: Long, context: Context) = viewModelScope.launch {
        messageRepository.getMessage(id).collect(){
            when(it){
                is NetworkResult.Success -> {
                    if (it.data?.success == true) {
                        it.data?.data?.let { messages ->
                            _listMessages.postValue(messages)
                        }
                        _isLoading.value = false
                    }
                    else {
                        _isLoading.value = false
                        Toast.makeText(context, it.data?.message, Toast.LENGTH_LONG).show()
                    }
                }
                is NetworkResult.Error -> {
                    _isLoading.value = false
                    Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    _isLoading.value = true
                }
            }
        }
    }

    fun init(userId: String) = viewModelScope.launch {
        WebSocketManager.init(serverUrl, messageListener, userId)
        WebSocketManager.connect()
    }

    override fun onConnectSuccess() {
        Log.d("conectWS", "connected")
    }

    override fun onConnectFailed() {
        Log.d("conectWS", "failed")
    }

    override fun onClose() {
        Log.d("conectWS", "closed")
    }

    override fun onMessage(text: String?) {
        Log.d("Message ", text ?: "")
        val mensaje = MessageSocket.fromJson(text ?: "")
        Log.d("Mensaje", mensaje.toString())
        if((_chatId.value).toInt() == (mensaje?.chat_id ?: 0)) {
            getMessages((mensaje?.chat_id ?: 0).toLong(), getApplication())
        }
    }
}