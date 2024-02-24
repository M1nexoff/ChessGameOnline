package uz.b7.chessonline.presenter.screen.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nasiyaapp.utils.myLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.b7.chessonline.R
import uz.b7.chessonline.data.model.LogInData
import uz.b7.chessonline.databinding.ScreenLoginBinding
import uz.b7.chessonline.presenter.activity.MainActivity
import kotlin.math.sin

@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.screen_login) {
    private val binding by viewBinding(ScreenLoginBinding::bind)
    private val viewModel:LoginVM by viewModels<LoginVMImpl>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButton()
    }

    fun initButton(){
        binding.apply {
            loginButton.setOnClickListener {
                viewModel.login(LogInData(phone.text.toString(),password.text.toString())).onEach {
                    it.onSuccess {
                        (requireActivity() as MainActivity).isOnlineUser()
                        findNavController().navigate(LoginScreenDirections.actionLoginScreenToSearchScreen())
                    }
                    it.onFailure {
                        Toast.makeText(requireContext(),it.message?:"Uncnowd error!",Toast.LENGTH_SHORT).show()
                        "${it.message}".myLog()
                    }
                }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
            }

            singup.setOnClickListener {
                findNavController().navigate(LoginScreenDirections.actionLoginScreenToSignUpScreen())
            }
        }
    }
}