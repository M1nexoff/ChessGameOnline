package uz.b7.chessonline.domain

import kotlinx.coroutines.flow.Flow
import uz.b7.chessonline.data.model.GameData

interface GameRepository {
    fun callbackFlowChessboard(): Flow<GameData>
    suspend fun startGame()
    suspend fun doTurn(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int): Boolean
}