package uz.b7.chessonline.data.sourse

import uz.b7.chessonline.data.model.UserData

interface MyShar {
    fun setUserInfo(user: String)
    fun getUserInfo():String
}