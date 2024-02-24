package uz.b7.chessonline.presenter.screen.play

import kotlinx.coroutines.flow.Flow
import uz.b7.chessonline.data.model.UserData

interface SearchVm {

    fun getAllActiveUser():Flow<Result<List<UserData>>>
}