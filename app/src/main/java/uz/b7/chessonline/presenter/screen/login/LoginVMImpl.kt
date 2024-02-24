package uz.b7.chessonline.presenter.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.b7.chessonline.data.model.LogInData
import uz.b7.chessonline.domain.LogInRepository
import javax.inject.Inject

@HiltViewModel
class LoginVMImpl @Inject constructor(
    private val logInRepository: LogInRepository
) : ViewModel(),LoginVM {
    override fun login(logInData: LogInData): Flow<Result<Unit>> =callbackFlow{
        logInRepository.logIn(logInData).onEach {
            it.onSuccess {
             trySend(Result.success(Unit))
            }
            it.onFailure {
                trySend(Result.failure(it))
            }


        }.launchIn(viewModelScope)
        awaitClose()
    }
}