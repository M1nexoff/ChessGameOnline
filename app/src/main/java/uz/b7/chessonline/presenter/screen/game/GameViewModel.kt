package uz.b7.chessonline.presenter.screen.game

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import uz.b7.chessonline.data.model.GameData

interface GameViewModel {
    fun startGame()
    val chessboard: SharedFlow<GameData>
    val isWhiteTurn: SharedFlow<Boolean>
    fun makeMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int)
}