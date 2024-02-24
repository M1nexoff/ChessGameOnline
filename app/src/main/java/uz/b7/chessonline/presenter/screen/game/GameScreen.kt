package uz.b7.chessonline.presenter.screen.game

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.b7.chessonline.R
import uz.b7.chessonline.data.model.GameData
import uz.b7.chessonline.data.sourse.MyShar
import uz.b7.chessonline.databinding.ScreenGameBinding
import javax.inject.Inject

@AndroidEntryPoint
class GameScreen : Fragment(R.layout.screen_game) {
    private val binding by viewBinding(ScreenGameBinding::bind)
    private val viewModel: GameViewModel by viewModels<GameViewModelImpl>()
    val list = arrayListOf(
        arrayListOf(2, 3, 4, 5, 6, 4, 3, 2),
        arrayListOf(1, 1, 1, 1, 1, 1, 1, 1),
        arrayListOf(0, 0, 0, 0, 0, 0, 0, 0),
        arrayListOf(0, 0, 0, 0, 0, 0, 0, 0),
        arrayListOf(0, 0, 0, 0, 0, 0, 0, 0),
        arrayListOf(0, 0, 0, 0, 0, 0, 0, 0),
        arrayListOf(7, 7, 7, 7, 7, 7, 7, 7),
        arrayListOf(8, 9, 10, 11, 12, 10, 9, 8)
    )

    @Inject
    lateinit var pref: MyShar
    private var chosenX = -1
    private var chosenY = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.chessboard.onEach {
            updateChessboard(it)
        }.launchIn(lifecycleScope)

        binding.newGame.setOnClickListener {
            viewModel.startGame()
        }

        for (i in list.indices) {
            val linear = binding.conteyner.getChildAt(i) as LinearLayout
            for (j in list[i].indices.reversed()) {
                val pieceImageView = linear.getChildAt(j) as ImageView
                pieceImageView.setImageResource(getPieceImageResource(list[i][j]))
                pieceImageView.setOnClickListener { onTouch(i, j) }
            }
        }
    }

    private fun onTouch(x: Int, y: Int) {
        if (chosenX == -1 && chosenY == -1 && list[x][y] != 0) {
            chosenX = x
            chosenY = y
        }
        if (chosenX in 0..7 && chosenY in 0..7 && x in 0..7 && y in 0..7 && !(chosenX == x && chosenY == y)) {
            viewModel.makeMove(chosenX, chosenY, x, y)
            chosenX = -1
            chosenY = -1
        }
        for (i in list.indices) {
            val linear = binding.conteyner.getChildAt(i) as LinearLayout
            for (j in list[i].indices.reversed()) {
                val pieceImageView = linear.getChildAt(j) as ImageView
                pieceImageView.setImageResource(getPieceImageResource(list[i][j]))
                pieceImageView.setOnClickListener { onTouch(i, j) }
            }
        }
    }

    private fun updateChessboard(data: GameData) {
        val isBlackUser = data.blackId == pref.getUserInfo()
        val reversedChessboard = if (isBlackUser) data.data.reversed() else data.data
        for (i in reversedChessboard.indices) {
            val linear = binding.conteyner.getChildAt(i) as LinearLayout
            for (j in 0 until reversedChessboard[i].size) {
                val pieceImageView = linear.getChildAt(j) as ImageView
                pieceImageView.setImageResource(getPieceImageResource(reversedChessboard[i][j]))
            }
        }
    }

    private fun getPieceImageResource(pieceType: Int): Int {
        return when (pieceType) {
            1 -> R.drawable.white_pawn
            2 -> R.drawable.white_rook
            3 -> R.drawable.white_knight
            4 -> R.drawable.white_bishop
            5 -> R.drawable.white_king
            6 -> R.drawable.white_queen
            7 -> R.drawable.black_pawn
            8 -> R.drawable.black_rook
            9 -> R.drawable.black_knight
            10 -> R.drawable.black_bishop
            11 -> R.drawable.black_king
            12 -> R.drawable.black_queen
            else -> R.drawable.empty

        }
    }
}
