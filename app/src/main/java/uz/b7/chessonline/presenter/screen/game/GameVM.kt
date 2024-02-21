package uz.b7.chessonline.presenter.screen.game

import kotlinx.coroutines.flow.StateFlow

interface GameVM {
    val isGame: StateFlow<Boolean>
}