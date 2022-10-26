package mx.com.satoritech.satoritickets.ui.chat

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.com.satoritech.domain.models.ChatWith
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.domain.models.UserChat
import mx.com.satoritech.satoritickets.repository.ChatRepository
import mx.com.satoritech.web.NetworkResult
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    val chatRepository: ChatRepository
): ViewModel() {

    private val _isLoading = mutableStateOf(false)
    var isLoading = _isLoading

    private val _listChat = mutableStateOf<UserChat?>(null)
    var listChat = _listChat
    fun getListChats(context: Context) = viewModelScope.launch {
        chatRepository.getListChats().collect(){
            when(it){
                is NetworkResult.Success -> {
                    if (it.data?.success == true) {
                        it.data?.data?.let { chats ->
                            _listChat.value = chats
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

    private val _chatWith = mutableStateOf<ChatWith?>(null)
    var chatWith = _chatWith

    fun getChat(id: Int, context: Context, success: (Boolean) -> Unit) = viewModelScope.launch {
        chatRepository.getChat(id).collect(){
            when(it){
                is NetworkResult.Success -> {
                    it.data?.data?.let { chat ->
                        _chatWith.value = chat
                    }
                    _isLoading.value = false
                    success(true)
                }
                is NetworkResult.Error -> {
                    _isLoading.value = false
                    Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_LONG).show()
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