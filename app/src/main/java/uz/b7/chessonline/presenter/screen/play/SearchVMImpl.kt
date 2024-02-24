package uz.b7.chessonline.presenter.screen.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.b7.chessonline.data.model.UserData
import uz.b7.chessonline.domain.SearchRepository
import javax.inject.Inject

@HiltViewModel
class SearchVMImpl @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel(), SearchVm {
    override fun getAllActiveUser(): Flow<Result<List<UserData>>> = callbackFlow{
        searchRepository.getAllActiveUser().onEach {
            it.onSuccess {
                trySend(Result.success(it))
            }
            it.onFailure {
                trySend(Result.failure(it))
            }
        }.launchIn(viewModelScope)
        awaitClose()
    }
}