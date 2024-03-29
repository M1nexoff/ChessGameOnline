package uz.b7.chessonline.presenter.screen.singUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.b7.chessonline.data.model.SingUpData
import uz.b7.chessonline.domain.SingUpRepository
import javax.inject.Inject
import kotlin.math.sin

@HiltViewModel
class SignUpVMImpl @Inject constructor(
    private val singUpRepository: SingUpRepository
) : ViewModel(),SignUpVm{
    override fun singUpUser(singUpData: SingUpData): Flow<Result<Unit>> = callbackFlow{
        singUpRepository.singUpUser(singUpData).onEach {
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