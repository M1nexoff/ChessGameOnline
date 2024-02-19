package uz.b7.chessonline.data.sourse

import android.content.Context
import android.content.SharedPreferences
import uz.b7.chessonline.data.model.UserData

object MyShar {
    private lateinit var sharedPreferences:SharedPreferences

    fun init(context: Context){
        sharedPreferences=context.getSharedPreferences("CheesGame",Context.MODE_PRIVATE)
    }

    fun setUserInfo(user: UserData){

    }
}