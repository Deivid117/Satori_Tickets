package mx.com.satoritech.satoritickets.ui.ticket

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mx.com.satoritech.domain.models.GenericResponse
import mx.com.satoritech.domain.models.Ticket
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.repository.TicketRepository
import mx.com.satoritech.satoritickets.utils.Extensions.isNumber
import mx.com.satoritech.satoritickets.utils.SearchWidgetState
import mx.com.satoritech.web.NetworkResult
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    val ticketRepository: TicketRepository
): ViewModel() {

    private val _loading = mutableStateOf(false)
    var loading = _loading

    private val _listTickets = MutableLiveData<List<Ticket>>(listOf())

    var listTickets = _listTickets

    private val ticketList = MutableLiveData<List<Ticket>>(listOf())

    fun getTickets(status: String,context: Context) = viewModelScope.launch {
        ticketRepository.getTickets(status).collect{
            when(it){
                is NetworkResult.Success -> {
                    it.data?.data?.let { ticket ->
                        _listTickets.postValue(ticket)
                        _loading.value = false
                    }
                }
                is NetworkResult.Error -> {
                    _loading.value = false
                    Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    _loading.value = true
                }
            }
        }
        ticketRepository.getTickets("all").collect{
            when(it){
                is NetworkResult.Success -> {
                    it.data?.data?.let { ticket ->
                        ticketList.postValue(ticket)
                    }
                }
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        }
    }

    private var _documentPDF = mutableStateOf<MultipartBody.Part?>(null)
    var documentPDF = _documentPDF

    private val _noTicket = mutableStateOf("")
    private val _project = mutableStateOf("")
    private val _device = mutableStateOf("")
    private val _date = mutableStateOf("")
    private val _content = mutableStateOf("")
    private val _userId = mutableStateOf("")
    private val _creatorId = mutableStateOf("")
    private val _pdf = mutableStateOf("")

    private val _noTicketError = mutableStateOf(0)
    private val _projectError = mutableStateOf(0)
    private val _contentError = mutableStateOf(0)

    private val _noTicketErrorBoolean = mutableStateOf(false)
    private val _projectErrorBoolean = mutableStateOf(false)
    private val _deviceErrorBoolean = mutableStateOf(false)
    private val _contentErrorBoolean = mutableStateOf(false)
    private val _userIdErrorBoolean = mutableStateOf(false)
    private val _pdfErrorBoolean = mutableStateOf(false)

    val noTicket: State<String> = _noTicket
    val project: State<String> = _project
    val device: State<String> = _device
    val content: State<String> = _content
    val userId: State<String> = _userId
    val pdf: State<String> = _pdf

    val noTicketError: State<Int> = _noTicketError
    val projectError: State<Int> = _projectError
    val contentError: State<Int> = _contentError

    val noTicketErrorBoolean: State<Boolean> = _noTicketErrorBoolean
    val projectErrorBoolean: State<Boolean> = _projectErrorBoolean
    val deviceErrorBoolean: State<Boolean> = _deviceErrorBoolean
    val contentErrorBoolean: State<Boolean> = _contentErrorBoolean
    val userIdErrorBoolean: State<Boolean> = _userIdErrorBoolean
    val pdfErrorBoolean: State<Boolean> = _pdfErrorBoolean

    fun setNoTicket(noTicket: String) {
        _noTicket.value = noTicket
    }
    fun setProject(project: String) {
        _project.value = project
    }
    fun setDevice(device: String) {
        _device.value = device
    }
    fun setDate(date: String) {
        _date.value = date
    }
    fun setContent(content: String) {
        _content.value = content
    }
    fun setUserId(userId: String) {
        _userId.value = userId
    }
    fun setCreatorId(creatorId: String) {
        _creatorId.value = creatorId
    }
    fun setPdf(pdf: String) {
        _pdf.value = pdf
    }

    fun setErrorNoTicket(request: Boolean){
        _noTicketErrorBoolean.value = request
    }
    fun setErrorProject(request: Boolean) {
        _projectErrorBoolean.value = request
    }
    fun setErrorDevice(request: Boolean){
        _deviceErrorBoolean.value = request
    }
    fun setErrorUser(request: Boolean){
        _userIdErrorBoolean.value = request
    }
    fun setErrorContent(request: Boolean) {
        _contentErrorBoolean.value = request
    }

    fun validate(): Boolean {
        val noTicket = this.noTicket.value
        val project = this.project.value
        val device = this.device.value
        val content = this.content.value
        val userId = this.userId.value
        val pdf = this.pdf.value
        var isValid = true

        if (noTicket.isEmpty()) {
            _noTicketError.value = R.string.errorEmpty
            _noTicketErrorBoolean.value = true
            isValid = false
        } else if(!noTicket.isNumber()) {
            _noTicketError.value = R.string.errorNumberField
            _noTicketErrorBoolean.value = true
        } else {
            _noTicketError.value = 0
            _noTicketErrorBoolean.value = false
        }
        if (project.isEmpty()) {
            _projectError.value = R.string.errorEmpty
            _projectErrorBoolean.value = true
            isValid = false
        } else {
            _projectError.value = 0
            _projectErrorBoolean.value = false
        }
        if (device.isEmpty()) {
            _deviceErrorBoolean.value = true
            isValid = false
        } else {
            _deviceErrorBoolean.value = false
        }
        if (content.isEmpty()) {
            _contentError.value = R.string.errorEmpty
            _contentErrorBoolean.value = true
            isValid = false
        } else {
            _contentError.value = 0
            _contentErrorBoolean.value = false
        }
        if (userId.isEmpty()) {
            _userIdErrorBoolean.value = true
            isValid = false
        } else {
            _userIdErrorBoolean.value = false
        }
        if (pdf.isEmpty()) {
            _pdfErrorBoolean.value = true
            isValid = false
        }
        else {
            _pdfErrorBoolean.value = false
        }
        return isValid
    }

    val showDialog = mutableStateOf(false)

    fun addTicket(context: Context, success: (Boolean) -> Unit) = viewModelScope.launch {
        if (validate()) {
            ticketRepository.addTicket(
                Ticket(
                    noTicket = _noTicket.value.toInt(),
                    project = _project.value.uppercase(),
                    team = _device.value,
                    date = _date.value,
                    content = _content.value,
                    userId = _userId.value.toInt(),
                    creatorId = _creatorId.value.toInt(),
                    status = 0
                ),
                pdf = _documentPDF.value
            ).collect() {
                when (it) {
                    is NetworkResult.Success -> {
                        if (it.data?.success == true) {
                            _loading.value = false
                            showDialog.value = true
                            success(true)
                        } else {
                            _loading.value = false
                            Toast.makeText(context, it.data?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    is NetworkResult.Error -> {
                        _loading.value = false
                        Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_LONG).show()
                        success(false)
                    }
                    is NetworkResult.Loading -> {
                        _loading.value = true
                        success(false)
                    }
                }
            }
        }
    }

    private val _listUser = MutableLiveData<List<User>>(listOf())
    var listUser = _listUser

    fun getUsers(context: Context) = viewModelScope.launch{
        ticketRepository.getUsers().collect {
            when(it){
                is NetworkResult.Success -> {
                    if (it.data?.success == true) {
                        it.data?.data?.let { user ->
                            _listUser.postValue(user)
                        }
                        _loading.value = false
                    }
                    else {
                        _loading.value = false
                        Toast.makeText(context, it.data?.message, Toast.LENGTH_LONG).show()
                    }
                }
                is NetworkResult.Error -> {
                    _loading.value = false
                    Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    _loading.value = true
                }
            }
        }
    }

    fun changeStatus(ticket_id: Long, status: Int, context: Context, success: (Boolean) -> Unit) = viewModelScope.launch {
        ticketRepository.changeStatus(ticket_id, status).collect {
            when(it){
                is NetworkResult.Success -> {
                    success(true)
                }
                is NetworkResult.Error -> {
                    success(false)
                    Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    success(false)
                }
            }
        }
    }

    private val _searchWidgetState: MutableState<SearchWidgetState> = mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    private val _matchedTickets = mutableStateOf<List<Ticket>?>(listOf())
    val matchedTickets: State<List<Ticket>?> = _matchedTickets

    private val _searchText = mutableStateOf("")
    var searchText: State<String> = _searchText

    fun setKeyWord(keyWord:String){
        _searchText.value = keyWord
    }

    fun filterContent(){
        if (ticketList.value?.isNotEmpty() == true && searchText.value.isNotBlank()){
            val ticketList = ticketList.value?: listOf()
            val keyWord = searchText.value.uppercase()

            val search:List<Ticket> = ticketList.filter { ticket ->
                ((ticket.noTicket?:0).toString()).contains(keyWord) ||
                        (ticket.project?:"").uppercase().contains(keyWord) ||
                        (ticket.team?:"").uppercase().contains(keyWord) ||
                        (ticket.date?:"").uppercase().contains(keyWord)
            }
            _matchedTickets.value = search
        } else {
            _matchedTickets.value =  ticketList.value?: listOf()
        }
    }

    fun onClearClick() {
        _searchText.value = ""
        _matchedTickets.value = arrayListOf()
    }

}
