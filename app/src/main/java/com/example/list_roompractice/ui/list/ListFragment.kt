package com.example.list_roompractice.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.list_roompractice.R
import com.example.list_roompractice.UsersListAdapter
import com.example.list_roompractice.data.UserViewModel
import com.example.list_roompractice.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private val viewModel:UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    private lateinit var binding:FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list,container,false)

        val adapter = UsersListAdapter()
        binding.userRecyclerview.adapter = adapter

        viewModel.readAllUsersData.observe(viewLifecycleOwner){
            if (it.isEmpty()){
                binding.emptyList.visibility = View.VISIBLE
            }else{
                if(binding.emptyList.visibility == View.VISIBLE){
                    binding.emptyList.visibility = View.GONE
                }
                adapter.submitList(it)
            }
        }


        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return binding.root
    }
}