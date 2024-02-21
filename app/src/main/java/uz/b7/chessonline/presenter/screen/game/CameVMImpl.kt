package uz.b7.chessonline.presenter.screen.game

import android.view.View
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class CameVMImpl: ViewModel(), GameVM {
    override val isGame = MutableStateFlow(false)

}