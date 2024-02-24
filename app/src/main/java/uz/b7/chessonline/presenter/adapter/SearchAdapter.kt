package uz.b7.chessonline.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.b7.chessonline.data.model.UserData
import uz.b7.chessonline.databinding.ItemSearchUserBinding
import uz.b7.chessonline.databinding.ScreenSplashBinding

class SearchAdapter:ListAdapter<UserData,SearchAdapter.Holder>(SearchAdapter.MyDiffUtil) {
    object MyDiffUtil:DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean =oldItem.id==newItem.id

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean =oldItem.name==newItem.name

    }

    inner class Holder(private val binding: ItemSearchUserBinding):ViewHolder(binding.root) {
        fun bind(){
            binding.userName.text=getItem(adapterPosition).name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(ItemSearchUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind()
    }


}