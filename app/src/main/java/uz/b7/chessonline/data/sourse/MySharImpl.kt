package uz.b7.chessonline.data.sourse
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.b7.chessonline.data.model.UserData
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class MySharImpl @Inject constructor(
    @ApplicationContext context: Context
):MyShar {
    private  var sharedPreferences:SharedPreferences
    init {
        sharedPreferences=context.getSharedPreferences("ChessGame",Context.MODE_PRIVATE)
    }
    override fun setUserInfo(user: UserData){
        sharedPreferences.edit().putString("id",user.id).apply()
        sharedPreferences.edit().putString("name",user.name).apply()
        sharedPreferences.edit().putInt("rating",user.rating).apply()
    }
    override fun getUserInfo(): UserData {
        val id=sharedPreferences.getString("id","")?:""
        val name=sharedPreferences.getString("name","")?:"Ali"
        val rating=sharedPreferences.getInt("rating",0)?:0
        return UserData(name,id,true,rating)
    }

}



