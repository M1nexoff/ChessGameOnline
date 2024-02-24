package uz.b7.chessonline.domain.impl

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.tasks.await
import uz.b7.chessonline.data.model.GameData
import uz.b7.chessonline.data.sourse.MyShar
import uz.b7.chessonline.domain.GameRepository
import javax.inject.Inject
import kotlin.math.abs

class GameRepositoryImpl @Inject constructor(private val pref: MyShar) : GameRepository {
    private val fireStore = FirebaseFirestore.getInstance()
    private val gamesCollection = fireStore.collection("games")

    private var gameDocumentId: String? = null

    override fun callbackFlowChessboard(): Flow<GameData> = callbackFlow {
        val listener = gamesCollection.document(gameDocumentId ?: "").addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val gameData = snapshot.toObject(GameData::class.java)
                if (gameData != null) {
                    trySend(gameData).isSuccess
                }
            }
        }
        awaitClose { listener.remove() }
    }

    override suspend fun startGame() {
        val initialChessboard = getInitialChessboard()
        gamesCollection.add(
            hashMapOf(
                "whiteId" to pref.getUserInfo(),
                "game" to initialChessboard,
                "isWhiteTurn" to true
            )
        ).addOnSuccessListener { documentReference ->
            gameDocumentId = documentReference.id
        }.await()
    }
    override suspend fun doTurn(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int): Boolean {
        val gameDocument = gamesCollection.document(gameDocumentId ?: "").get().await()
        val chessboard = gameDocument.get("game") as ArrayList<ArrayList<Int>>
        val isWhiteTurn = gameDocument.getBoolean("isWhiteTurn") ?: true

        if ((isWhiteTurn && chessboard[fromRow][fromCol] in 1..6) ||
            (!isWhiteTurn && chessboard[fromRow][fromCol] in 7..12)
        ) {
            if (isValidMove(chessboard, fromRow, fromCol, toRow, toCol,isWhiteTurn)) {
                chessboard[toRow][toCol] = chessboard[fromRow][fromCol]
                chessboard[fromRow][fromCol] = 0

                gamesCollection.document(gameDocumentId ?: "").update(
                    "game" , chessboard,
                    "isWhiteTurn" , !isWhiteTurn
                ).await()

                return true
            }
        }
        return false
    }

    private fun getInitialChessboard(): List<List<Int>> {
        return listOf(
            listOf(8, 9, 10, 12, 11, 10, 9, 8),
            listOf(7, 7, 7, 7, 7, 7, 7, 7),
            listOf(0, 0, 0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0, 0, 0),
            listOf(1, 1, 1, 1, 1, 1, 1, 1),
            listOf(2, 3, 4, 6, 5, 4, 3, 2)
        )
    }

    private fun isValidMove(
        chessboard: List<List<Int>>,
        fromRow: Int,
        fromCol: Int,
        toRow: Int,
        toCol: Int,
        isWhiteTurn: Boolean
    ): Boolean {
        if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8) {
            return false
        }

        val pieceType = chessboard[fromRow][fromCol]

        val isWhitePiece = pieceType in 1..6
        val isBlackPiece = pieceType in 9..16
        if (isWhitePiece && !isWhiteTurn || isBlackPiece && isWhiteTurn) {
            return false
        }

        when (pieceType) {
            1, 7 -> {
                val direction = if (isWhiteTurn) 1 else -1
                if (toCol == fromCol && chessboard[toRow][toCol] == 0) {
                    if (toRow == fromRow + direction) {
                        return true
                    }
                    if (fromRow == (if (isWhiteTurn) 1 else 6) && toRow == fromRow + 2 * direction && chessboard[fromRow + direction][fromCol] == 0) {
                        return true
                    }
                }
                return abs(toCol - fromCol) == 1 && toRow == fromRow + direction && chessboard[toRow][toCol] != 0
            }
            2, 8 -> {
                if (fromRow == toRow || fromCol == toCol) {
                    if (isPathClear(chessboard, fromRow, fromCol, toRow, toCol)) {
                        return true
                    }
                }
                return false
            }
            3, 9 -> {
                val rowDiff = abs(toRow - fromRow)
                val colDiff = abs(toCol - fromCol)
                return (rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1)
            }
            4, 10 -> {
                if (abs(toRow - fromRow) == abs(toCol - fromCol)) {
                    if (isPathClear(chessboard, fromRow, fromCol, toRow, toCol)) {
                        return true
                    }
                }
                return false
            }
            5, 11 -> {
                if ((fromRow == toRow || fromCol == toCol) || abs(toRow - fromRow) == abs(toCol - fromCol)) {
                    if (isPathClear(chessboard, fromRow, fromCol, toRow, toCol)) {
                        return true
                    }
                }
                return false
            }
            6, 12 -> {
                val rowDiff = abs(toRow - fromRow)
                val colDiff = abs(toCol - fromCol)
                return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1) || (rowDiff == 1 && colDiff == 1)
            }
            else -> return false
        }
    }

    private fun isPathClear(
        chessboard: List<List<Int>>,
        fromRow: Int,
        fromCol: Int,
        toRow: Int,
        toCol: Int
    ): Boolean {
        val rowIncrement = if (toRow > fromRow) 1 else if (toRow < fromRow) -1 else 0
        val colIncrement = if (toCol > fromCol) 1 else if (toCol < fromCol) -1 else 0

        var currentRow = fromRow + rowIncrement
        var currentCol = fromCol + colIncrement

        while (currentRow != toRow || currentCol != toCol) {
            if (chessboard[currentRow][currentCol] != 0) {
                return false
            }
            currentRow += rowIncrement
            currentCol += colIncrement
        }
        return true
    }

}
