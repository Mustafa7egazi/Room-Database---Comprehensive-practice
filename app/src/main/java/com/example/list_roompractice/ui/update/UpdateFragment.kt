package com.example.list_roompractice.ui.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.list_roompractice.R
import com.example.list_roompractice.data.User
import com.example.list_roompractice.data.UserViewModel
import com.example.list_roompractice.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        setHasOptionsMenu(true)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.deleteAction -> {
                        deleteUser()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_update, container, false)
        binding.firstNameEtUpdate.setText(args.currentUser.firstName)
        binding.lastNameEtUpdate.setText(args.currentUser.lastName)
        binding.ageEtUpdate.setText(args.currentUser.age.toString())


        binding.updateUser.setOnClickListener {
            updateUser()
        }
        return binding.root
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setPositiveButton("Yes") { _, _ ->

                val fName = binding.firstNameEtUpdate.text.toString()
                val lName = binding.lastNameEtUpdate.text.toString()
                val age = binding.ageEtUpdate.text.toString()
                val userId = args.currentUser.id
                if (validUserInput(fName, lName, age)) {
                    val userToBeDeleted: User = User(userId, fName, lName, age.toInt())
                    viewModel.deleteUser(userToBeDeleted)
                    Toast.makeText(requireContext(), "$fName $lName deleted!", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_updateFragment_to_listFragment2)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error occurred while deleting $fName $lName!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            setNegativeButton("No",null)

            setTitle("Delete ${args.currentUser.firstName}")
            setMessage("Are you sure you want to delete ${args.currentUser.firstName} ${args.currentUser.lastName}?")
                .create()
                .show()
        }
    }

    private fun updateUser() {
        val fName = binding.firstNameEtUpdate.text.toString()
        val lName = binding.lastNameEtUpdate.text.toString()
        val age = binding.ageEtUpdate.text.toString()
        if (validUserInput(fName, lName, age)) {
            val updatedUser = User(args.currentUser.id, fName, lName, age.toInt())
            viewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment2)
        } else {
            Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validUserInput(firstName: String, lastName: String, age: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(
            age
        ))
    }
}