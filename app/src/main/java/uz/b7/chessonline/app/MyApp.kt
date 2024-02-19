package uz.b7.chessonline.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import uz.b7.chessonline.data.sourse.MyShar

@HiltAndroidApp
class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.asTree())
    }
}