package com.example.list_roompractice.ui.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.list_roompractice.R
import com.example.list_roompractice.data.User
import com.example.list_roompractice.data.UserViewModel
import com.example.list_roompractice.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private lateinit var binding:FragmentAddBinding
    private val viewModel:UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add,container,false)

        binding.addNewUser.setOnClickListener {
            insertUserInDatabase()
        }
        return binding.root
    }

    private fun validUserInput(firstName:String , lastName:String , age:String): Boolean {
     return !(TextUtils.isEmpty(firstName) &&TextUtils.isEmpty(lastName) &&TextUtils.isEmpty(age))
    }

    private fun insertUserInDatabase() {
        val fName = binding.firstNameEt.text.toString()
        val lName = binding.lastNameEt.text.toString()
        val age = binding.ageEt.text.toString()
        if (validUserInput(fName,lName,age)){
            viewModel.addUser(User(0,fName,lName,age.toInt()))
            Toast.makeText(requireContext(),"Successfully inserted!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"All fields are required!",Toast.LENGTH_SHORT).show()
        }
    }
}