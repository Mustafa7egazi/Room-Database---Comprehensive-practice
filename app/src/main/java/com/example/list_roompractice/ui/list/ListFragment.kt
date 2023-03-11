package com.example.list_roompractice.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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

        //Menu creating
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.deleteAction -> {
                        clearAllData()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)

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

    private fun clearAllData() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAll()
                Toast.makeText(requireContext(),"All data has gone forever!",Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("No",null)
            setTitle("Clear Data")
            setMessage("Are you sure you want to delete all data!")
                .create()
                .show()
        }

    }
}