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
        sharedPreferences.edit().putString("name",user.name).apply()
        sharedPreferences.edit().putString("id",user.id).apply()
    }

    fun getUserInfo():UserData{
        val name= sharedPreferences.getString("name","")?:""
        val id= sharedPreferences.getString("id","")?:""
        return UserData(name,id)
    }

}