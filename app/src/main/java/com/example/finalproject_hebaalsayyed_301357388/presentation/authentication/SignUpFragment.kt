package com.example.finalproject_hebaalsayyed_301357388.presentation.authentication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalproject_hebaalsayyed_301357388.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment: Fragment() {
    lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener()
        setUpTextWatchers()
    }

    private fun setUpTextWatchers() {
        binding.etPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validatePassword()
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.etPassConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateConfirmPassword()
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateEmail()
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun validatePassword() {
        val password = binding.etPass.text.toString()
        if (password.length < 8) {
            binding.passwordLayout.error = "Password must be at least 8 characters"
        } else {
            binding.passwordLayout.error = null
        }
    }

    private fun validateConfirmPassword() {
        val password = binding.etPass.text.toString()
        val confirmPassword = binding.etPassConfirm.text.toString()
        if (password != confirmPassword) {
            binding.confirmPasswordLayout.error = "Passwords do not match"
        } else {
            binding.confirmPasswordLayout.error = null
        }
    }

    private fun listener() {
        binding.btnSignUp.setOnClickListener {
            signUp()
        }
        binding.tvSignIn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun signUp() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPass.text.toString()
        val confirmPassword = binding.etPassConfirm.text.toString()

        var isValid = true

        binding.passwordLayout.error = null
        binding.confirmPasswordLayout.error = null

        if (password.length < 8) {
            binding.passwordLayout.error = "Password must be at least 8 characters"
            isValid = false
        }

        if (password != confirmPassword) {
            binding.confirmPasswordLayout.error = "Passwords do not match"
            isValid = false
        }

        if (email.isEmpty()) {
            binding.emailLayout.error = "Email is required"
            isValid = false
        }

        if (isValid) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    } else {
                        Toast.makeText(context, "Account creation failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun validateEmail() {
        val email = binding.etEmail.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!email.matches(emailPattern.toRegex())) {
            binding.emailLayout.error = "Invalid email format"
        } else {
            binding.emailLayout.error = null
        }
    }
}