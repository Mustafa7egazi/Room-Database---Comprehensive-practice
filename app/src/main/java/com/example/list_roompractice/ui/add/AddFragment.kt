package com.example.list_roompractice.ui.add

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.list_roompractice.R
import com.example.list_roompractice.data.User
import com.example.list_roompractice.data.UserViewModel
import com.example.list_roompractice.databinding.FragmentAddBinding
import com.jakewharton.byteunits.BinaryByteUnit

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    private lateinit var userPic: Bitmap

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            // Handle the returned URI here
            if (uri != null) {
                uri.let {
                    val contentResolver = requireContext().contentResolver
                    val inputStream = contentResolver.openInputStream(it)
                    val imageSize = inputStream?.available() ?: 0
                    inputStream?.close()
                    val MAX_IMAGE_SIZE = BinaryByteUnit.MEBIBYTES.toBytes(2)
                    if (imageSize > MAX_IMAGE_SIZE) {
                        // The selected image exceeds the maximum allowed size
                        // Show an error message to the user
                        Toast.makeText(
                            requireContext(),
                            "Selected image is too large",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        binding.addNewUser.isEnabled = true
                        binding.hint.visibility = View.GONE
                        binding.correct.visibility = View.VISIBLE
                        userPic = getBitmapFromUri(uri)
                    }
                }
            } else {
                binding.addNewUser.isEnabled = false
                Toast.makeText(
                    requireContext(),
                    "No Image selected, please re-select one",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)



        binding.pickPhoto.setOnClickListener {
            pickImageFromGallery()
        }

        binding.addNewUser.setOnClickListener {
            insertUserInDatabase()
        }
        return binding.root
    }

    private fun pickImageFromGallery() {
        getContent.launch("image/*")
    }

    private fun validUserInput(firstName: String, lastName: String, age: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(
            age
        ))
    }

    private fun insertUserInDatabase() {
        val fName = binding.firstNameEt.text.toString()
        val lName = binding.lastNameEt.text.toString()
        val age = binding.ageEt.text.toString()
        if (validUserInput(fName, lName, age)) {
            viewModel.addUser(User(0, fName, lName, age.toInt(), userPic))
            Toast.makeText(requireContext(), "Successfully inserted!", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "All fields are required!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val inputStream = context?.contentResolver?.openInputStream(uri)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(inputStream, null, options)
        inputStream?.close()

        val imageHeight = options.outHeight
        val imageWidth = options.outWidth
        var scaleFactor = 1

        if (imageHeight > 1024 || imageWidth > 1024) {
            scaleFactor = Math.max(imageHeight / 1024, imageWidth / 1024)
        }

        options.inJustDecodeBounds = false
        options.inSampleSize = scaleFactor

        val bitmap = BitmapFactory.decodeStream(
            context?.contentResolver?.openInputStream(uri),
            null,
            options
        )

        return bitmap!!
    }
}