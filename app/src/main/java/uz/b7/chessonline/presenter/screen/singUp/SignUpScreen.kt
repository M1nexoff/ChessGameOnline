package uz.b7.chessonline.presenter.screen.singUp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nasiyaapp.utils.myLog
import com.example.nasiyaapp.utils.myShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.b7.chessonline.R
import uz.b7.chessonline.data.model.SingUpData
import uz.b7.chessonline.databinding.ScreenSingupBinding
import uz.b7.chessonline.presenter.activity.MainActivity
import uz.b7.chessonline.presenter.screen.login.LoginVM
import uz.b7.chessonline.presenter.screen.login.LoginVMImpl

@AndroidEntryPoint
class SignUpScreen : Fragment(R.layout.screen_singup) {
    private val binding by viewBinding(ScreenSingupBinding::bind)
    private val viewModel: SignUpVm by viewModels<SignUpVMImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButtons()
    }

    fun initButtons(){
        binding.apply {
            singupButton.setOnClickListener {
                viewModel.singUpUser(SingUpData(name.text.toString(),phone.text.toString(),password.text.toString())).onEach {
                    it.onSuccess {
                        (requireActivity() as MainActivity).isOnlineUser()
                        findNavController().navigate(SignUpScreenDirections.actionSignUpScreenToSearchScreen())
                    }
                    it.onFailure {
                        "${it.message}".myShortToast(requireContext())
                        "${it.message}".myLog()
                    }

                }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
            }
            login.setOnClickListener {
                findNavController().navigate(SignUpScreenDirections.actionSignUpScreenToLoginScreen())
            }
        }
    }

}