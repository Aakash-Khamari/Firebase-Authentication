package com.sovereign.firebaseauthentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.sovereign.firebaseauthentication.databinding.ActivityMainBinding
import com.sovereign.firebaseauthentication.databinding.FragmentGetStartedBinding

class getStartedFragment : Fragment() {

    private var _binding: FragmentGetStartedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGetStartedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Use binding to access views
        binding.getStarted.setOnClickListener(){
            findNavController().navigate(R.id.action_getStartedFragment_to_signInFragment)
        }

        val auth = Firebase.auth

        if (auth.currentUser!=null)
        {
            findNavController().navigate(R.id.action_getStartedFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}