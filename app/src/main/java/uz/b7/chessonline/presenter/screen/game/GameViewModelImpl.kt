package uz.b7.chessonline.presenter.screen.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.b7.chessonline.data.model.GameData
import uz.b7.chessonline.domain.GameRepository
import javax.inject.Inject

@HiltViewModel
class GameViewModelImpl @Inject constructor(private val gameRepository: GameRepository) :
    ViewModel(), GameViewModel {

    private val _chessboard = MutableSharedFlow<GameData>()
    override val chessboard: SharedFlow<GameData> = _chessboard

    private val _isWhiteTurn = MutableSharedFlow<Boolean>()
    override val isWhiteTurn: SharedFlow<Boolean> = _isWhiteTurn

    override fun startGame() {
        gameRepository.callbackFlowChessboard().onEach {
            _chessboard.tryEmit(it)
        }.launchIn(viewModelScope)
        _isWhiteTurn.tryEmit(true)
    }

    override fun makeMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int) {
        viewModelScope.launch {
            gameRepository.doTurn(fromRow, fromCol, toRow, toCol)
        }
    }
}
