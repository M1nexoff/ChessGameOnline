package uz.b7.chessonline.presenter.screen.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.b7.chessonline.R
import uz.b7.chessonline.data.sourse.MyShar
import uz.b7.chessonline.databinding.ScreenSplashBinding
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreen:Fragment(R.layout.screen_splash) {
    private val binding by viewBinding(ScreenSplashBinding::bind)
    @Inject lateinit var myShar: MyShar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        lifecycleScope.launch {
            delay(1000)

            if (myShar.getUserInfo().id!=""){
                findNavController().navigate(SplashScreenDirections.actionSplashScreenToSearchScreen())
            }else{
                findNavController().navigate(SplashScreenDirections.actionSplashScreenToLoginScreen())
            }
        }
    }
}