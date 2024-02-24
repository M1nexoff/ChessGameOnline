package uz.b7.chessonline.presenter.screen.login

import kotlinx.coroutines.flow.Flow
import uz.b7.chessonline.data.model.LogInData

interface LoginVM {
    fun login(logInData: LogInData):Flow<Result<Unit>>
}