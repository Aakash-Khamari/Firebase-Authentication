package com.sovereign.firebaseauthentication

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.firebase.auth.FirebaseAuth
import com.sovereign.firebaseauthentication.databinding.FragmentForgetpasswordBinding

class ForgetpasswordFragment : Fragment() {
    private var _binding: FragmentForgetpasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgetpasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Use binding to access views
        firebaseAuth = FirebaseAuth.getInstance()

        binding.ForgotPasswordEmail.doAfterTextChanged {
            binding.tvWarningMsg.visibility = View.GONE
        }

        binding.btnForgotPasswordSubmit.setOnClickListener {
            val email = binding.ForgotPasswordEmail.text.toString()
            if (email.isEmpty()) {
                binding.tvWarningMsg.text = "Please enter your email address"
                binding.tvWarningMsg.visibility = View.VISIBLE
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.tvWarningMsg.text = "Please enter a valid email address"
                binding.tvWarningMsg.visibility = View.VISIBLE
            } else {
                hideKeyboard()

                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {task->
                    if (task.isSuccessful)
                    {
                        binding.tilEmailForgetPassword.visibility = View.GONE
                        binding.btnForgotPasswordSubmit.visibility = View.GONE
                        binding.tvSubmitMsg.visibility = View.VISIBLE
                    }
                    else{
                        Toast.makeText(requireContext(), "Can not reset Password. Try again later", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

    }
    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}