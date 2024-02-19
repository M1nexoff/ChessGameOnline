package uz.b7.chessonline.app

import android.app.Application
import uz.b7.chessonline.data.sourse.MyShar

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        MyShar.init(this)
    }
}