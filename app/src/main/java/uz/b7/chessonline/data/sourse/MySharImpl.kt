package uz.b7.chessonline.data.sourse
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
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
    override fun setUserInfo(user: String){
        sharedPreferences.edit().putString("id",user).apply()
    }
    override fun getUserInfo(): String {
        return sharedPreferences.getString("id", "") ?: ""
    }

}



