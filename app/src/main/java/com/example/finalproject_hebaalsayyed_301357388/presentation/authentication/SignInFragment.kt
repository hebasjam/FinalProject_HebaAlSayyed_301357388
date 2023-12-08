package com.example.finalproject_hebaalsayyed_301357388.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalproject_hebaalsayyed_301357388.R
import com.example.finalproject_hebaalsayyed_301357388.databinding.FragmentSigninBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment: Fragment() {
    lateinit var binding: FragmentSigninBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSignIn.setOnClickListener {
            if (validateForm()) {
                signIn()
            }
        }
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun validateForm(): Boolean {
        val email = binding.etEmail.text.toString()
        val password = binding.etPass.text.toString()

        var isValid = true

        if (email.isEmpty()) {
            binding.emailLayout.error = "Email cannot be empty"
            isValid = false
        } else {
            binding.emailLayout.error = null
        }

        if (password.isEmpty()) {
            binding.passwordLayout.error = "Password cannot be empty"
            isValid = false
        } else {
            binding.passwordLayout.error = null
        }

        return isValid
    }

    private fun signIn() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPass.text.toString()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Signed In Successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signInFragment_to_optionsFragment)
                } else {
                    Toast.makeText(context, "Error Check Your Information", Toast.LENGTH_SHORT).show()
                }
            }
    }
}