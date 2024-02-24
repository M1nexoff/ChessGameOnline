package uz.b7.chessonline.domain

import kotlinx.coroutines.flow.Flow
import uz.b7.chessonline.data.model.SingUpData

interface SingUpRepository {

    fun singUpUser(singUpData: SingUpData):Flow<Result<Unit>>
}