package uz.b7.chessonline.domain

import kotlinx.coroutines.flow.Flow
import uz.b7.chessonline.data.model.LogInData

interface LogInRepository {


    fun logIn(logInData: LogInData):Flow<Result<Unit>>
}