package uz.b7.chessonline.domain

import kotlinx.coroutines.flow.Flow
import uz.b7.chessonline.data.model.UserData

interface SearchRepository {

    fun getAllActiveUser():Flow<Result<List<UserData>>>
}