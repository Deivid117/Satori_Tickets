package mx.com.satoritech.satoritickets.ui.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.com.satoritech.domain.models.Login
import mx.com.satoritech.domain.models.User
import mx.com.satoritech.satoritickets.R
import mx.com.satoritech.satoritickets.repository.AuthRepository
import mx.com.satoritech.satoritickets.repository.LocalUserRepository
import mx.com.satoritech.satoritickets.utils.Extensions.isEmail
import mx.com.satoritech.web.NetworkResult
import mx.com.satoritech.web.Token
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val localUserRepository: LocalUserRepository
): ViewModel() {

    val user = localUserRepository.getWithLiveData()

    private val _loggedUser = mutableStateOf<User?>(null)
    val loggedUser = _loggedUser

    private val _email = mutableStateOf("")
    private val _password = mutableStateOf("")

    private val _emailError = mutableStateOf(0)
    private val _passwordError = mutableStateOf(0)

    private val _emailErrorBoolean = mutableStateOf(false)
    private val _passwordErrorBoolean = mutableStateOf(false)

    val email: State<String> = _email
    val password: State<String> = _password

    val emailError: State<Int> = _emailError
    val passwordError: State<Int> = _passwordError

    val emailErrorBoolean: State<Boolean> = _emailErrorBoolean
    val passwordErrorBoolean: State<Boolean> = _passwordErrorBoolean

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setErrorEmail(request: Boolean) {
        _emailErrorBoolean.value = request
    }
    fun setErrorPassword(request: Boolean) {
        _passwordErrorBoolean.value = request
    }

    fun validate(): Boolean {
        val email = this.email.value
        val password = this.password.value
        var isValid = true

        if (email.isEmpty()) {
            _emailError.value = R.string.errorEmpty
            _emailErrorBoolean.value = true
            isValid = false
        } else if (!email.isEmail()) {
            _emailError.value = R.string.errorInvalidField
            _emailErrorBoolean.value = true
            isValid = false
        } else {
            _emailError.value = 0
            _emailErrorBoolean.value = false
        }

        if (password.isEmpty()) {
            _passwordError.value = R.string.errorEmpty
            _passwordErrorBoolean.value = true
            isValid = false
        } else {
            _passwordError.value = 0
            _passwordErrorBoolean.value = false
        }
        return isValid
    }

    fun login(success: (Boolean) -> Unit, context: Context) = viewModelScope.launch {
        if (validate()) {
            authRepository.login(
                Login(
                    email = _email.value,
                    password = _password.value,
                )
            ).collect {
                when(it){
                    is NetworkResult.Success -> {
                        if (it.data?.success == true) {
                            it.data?.data?.let { user ->
                                _loggedUser.value = user
                                localUserRepository.upsert(user)
                                Token.token = user.apiToken ?: ""
                                success(true)
                            }
                        }
                        else {
                            _isLoading.value = false
                            Toast.makeText(context, it.data?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    is NetworkResult.Error -> {
                        _isLoading.value = false
                        _loggedUser.value = null
                        Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG).show()
                        success(false)
                    }
                    is NetworkResult.Loading -> {
                        _isLoading.value = true
                    }
                }
            }
        }
    }

    fun logout() = viewModelScope.launch {
        localUserRepository.deleteAll()
    }
}