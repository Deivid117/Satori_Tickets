package mx.com.satoritech.satoritickets.ui.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mx.com.satoritech.satoritickets.repository.LocalUserRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    localUserRepository: LocalUserRepository
): ViewModel() {

    val user = localUserRepository.getWithLiveData()

}