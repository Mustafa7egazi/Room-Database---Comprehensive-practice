package com.example.list_roompractice

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.list_roompractice.data.User
import com.example.list_roompractice.databinding.ListItemLayoutBinding
import com.example.list_roompractice.ui.list.ListFragmentDirections


class UsersListAdapter : ListAdapter<User, UsersListAdapter.ViewHolder>(UserDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.userId.text = getItem(position).id.toString()
        holder.binding.userName.text = getItem(position).firstName+" "+getItem(position).lastName
        holder.binding.userAge.text = getItem(position).age.toString()
        holder.binding.imageView.setImageBitmap(getItem(position).profilePic)

        holder.binding.itemOfList.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(getItem(position))
            it.findNavController().navigate(action)
        }
    }

    inner class ViewHolder(val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User, newItem:
            User
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem


            /*fun setData(newList:List<Person>){
                   val diffUtil = MyDiffUtil(oldPersonsList,newList)
                   val diffResults = DiffUtil.calculateDiff(diffUtil)
                   oldPersonsList = newList
                   diffResults.dispatchUpdatesTo(this)
               }*/
        }

    }
    /*inner class MyDiffUtil(
        private val oldList:List<Person>,
        private val newList:List<Person>
        ):DiffUtil.Callback(){

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id== newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
          return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }*/
}