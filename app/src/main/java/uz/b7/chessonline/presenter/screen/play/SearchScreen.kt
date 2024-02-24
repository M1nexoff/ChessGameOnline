package uz.b7.chessonline.presenter.screen.play

import android.os.Bundle
import android.system.Os.bind
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nasiyaapp.utils.myLog
import com.example.nasiyaapp.utils.myShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.b7.chessonline.R
import uz.b7.chessonline.databinding.ScreenGameBinding
import uz.b7.chessonline.databinding.ScreenSearchBinding
import uz.b7.chessonline.presenter.adapter.SearchAdapter

@AndroidEntryPoint
class SearchScreen : Fragment(R.layout.screen_search){
    private val binding by viewBinding(ScreenSearchBinding::bind)
    private val adapter by lazy { SearchAdapter() }
    private val viewModel:SearchVm by viewModels<SearchVMImpl>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initAdapter()
    }

    private fun initViewModel(){
        viewModel.getAllActiveUser().onEach { res->
            res.onSuccess {
                adapter.submitList(it)
            }
            res.onFailure {
                "${it.message}".myShortToast(requireContext())
                "${it.message}".myLog()
            }

        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
    }

    private fun initAdapter(){
        binding.apply {
            recyclerView.adapter=adapter
            recyclerView.layoutManager=LinearLayoutManager(requireContext())
        }
    }
}