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

class ListFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private val viewModel:UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    private val adapter by lazy {
        UsersListAdapter()
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

                val search = menu.findItem(R.id.searchAction)
                val searchView = search.actionView as androidx.appcompat.widget.SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(this@ListFragment)
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


        binding.userRecyclerview.adapter = adapter

        observeOnAllUsersData()


        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return binding.root
    }

    private fun observeOnAllUsersData() {
        viewModel.readAllUsersData.observe(viewLifecycleOwner){
            if (it.isEmpty()){
                binding.emptyList.visibility = View.VISIBLE
                binding.userRecyclerview.visibility = View.GONE
            }else{
                if(binding.emptyList.visibility == View.VISIBLE){
                    binding.userRecyclerview.visibility = View.VISIBLE
                    binding.emptyList.visibility = View.GONE
                }
                adapter.submitList(it)
            }
        }
    }

    private fun clearAllData() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAll()
                Toast.makeText(requireContext(),"All data has gone forever!",Toast.LENGTH_SHORT).show()
                observeOnAllUsersData()
            }
            setNegativeButton("No",null)
            setTitle("Clear Data")
            setMessage("Are you sure you want to delete all data!")
                .create()
                .show()
        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       if (query != null){
           searchDatabase(query)
       }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query:String){
        val searchQuery = "%$query%"
        viewModel.searchAboutUser(searchQuery).observe(viewLifecycleOwner){list->
            list.let {
             adapter.submitList(it)
            }
        }
    }
}