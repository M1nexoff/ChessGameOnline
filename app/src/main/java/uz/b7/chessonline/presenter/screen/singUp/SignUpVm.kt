package uz.b7.chessonline.presenter.screen.singUp

import kotlinx.coroutines.flow.Flow
import uz.b7.chessonline.data.model.SingUpData

interface SignUpVm {

    fun singUpUser(singUpData: SingUpData):Flow<Result<Unit>>
}