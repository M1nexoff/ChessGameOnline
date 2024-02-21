package uz.b7.chessonline.domain.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uz.b7.chessonline.data.model.Figure
import uz.b7.chessonline.data.sourse.MyShar
import uz.b7.chessonline.domain.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(val pref: MyShar) : GameRepository {
    private val fireStore = Firebase.firestore
    var userName = "Anonymous"
        private set

    private var chessboard = ArrayList<ArrayList<Int>>()
    private var isBlackUser = false
    private var gameDocumentId: String? = null

    init {
        if (pref.getUserInfo() == "") {
            fireStore.collection("users").add(
                hashMapOf(
                    "name" to "Anonymous", "isActive" to false
                )
            )
        } else {
            fireStore.collection("users").document(pref.getUserInfo()).get().addOnSuccessListener {
                userName = it.data!!.getOrDefault("name", "Anonymous") as String
            }
        }
        initializeChessboard()
        placeInitialFigures()
    }

    private fun initializeChessboard() {
        for (i in 0 until 8) {
            chessboard.add(ArrayList())
            for (j in 0 until 8) {
                chessboard[i].add(0)
            }
        }
    }

    private fun placeInitialFigures() {
        val figureTypes = arrayOf(2, 3, 4, 5, 6, 4, 3, 2)
        for (row in 0 until 8) {
            for (col in 0 until 8) {
                val figureType = figureTypes[col]
                val figure = when (row) {
                    0 -> Figure(figureType, true, getFigureName(figureType))
                    1 -> Figure(1, true, "Pawn")
                    6 -> Figure(10, false, "Pawn")
                    7 -> Figure(figureType + 8, false, getFigureName(figureType))
                    else -> null
                }
                figure?.let {
                    chessboard[row][col] = figureType
                }
            }
        }

        if (isBlackUser) {
            chessboard.reverse()
            for (row in chessboard) {
                row.reverse()
            }
        }
    }

    private fun getFigureName(figureType: Int): String {
        return when (figureType) {
            2 -> "Rook"
            3 -> "Knight"
            4 -> "Bishop"
            5 -> "Queen"
            6 -> "King"
            else -> "Unknown"
        }
    }

    fun startGame() {
        val newGameDocument = fireStore.collection("game").add(hashMapOf(
            "whiteId" to pref.getUserInfo(),
            "game" to chessboard
        )).addOnSuccessListener {
            gameDocumentId = it.id
        }
    }

    fun doTurn(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int): Boolean {
        if (!canDoTurn(fromRow, fromCol, toRow, toCol)) {
            return false
        }

        val figureType = chessboard[fromRow][fromCol]
        chessboard[toRow][toCol] = figureType
        chessboard[fromRow][fromCol] = 0

        // Update the chessboard in Firestore
        gameDocumentId?.let { gameId ->
            fireStore.collection("game").document(gameId).update("chessboard", chessboard)
        }

        return true
    }

    fun canDoTurn(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int): Boolean {
        val fromFigureType = chessboard[fromRow][fromCol]
        if (fromFigureType == 0) {
            return false
        }

        if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8) {
            return false
        }

        val toFigureType = chessboard[toRow][toCol]
        if (fromFigureType.isWhite() == toFigureType.isWhite()) {
            return false
        }

        return true
    }

    fun didOpponentTurn(): Boolean {
        return false;

    }

    private fun Int.isWhite(): Boolean {
        return this in 1..6
    }
}
