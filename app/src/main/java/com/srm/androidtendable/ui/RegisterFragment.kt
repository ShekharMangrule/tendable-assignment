package com.srm.androidtendable.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.srm.androidtendable.R
import com.srm.androidtendable.databinding.FragmentRegisterBinding
import com.srm.androidtendable.utils.UIState
import com.srm.androidtendable.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoader(true)
        initData()
        //showLoader(false)

        viewModel.registerUserResult.observe(viewLifecycleOwner) {
            when(it) {
                is UIState.Loading -> {
                    showLoader(false)
                }
                is UIState.Success -> {
                    //Navigate to Login
                    showLoader(true)
                    Toast.makeText(
                        context,
                        "Registration successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    view.findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                }
                is UIState.Error -> {
                    showLoader(true)
                    Toast.makeText(
                        context,
                        "There is something wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else -> {}
            }
        }

        binding.btnRegister.setOnClickListener {
            lifecycleScope.launch {
                binding.apply {
                    viewModel.register()
                }
            }
        }

    }

    private fun initData() {
        binding.txtEmail.setText("johnd@email.com")
        binding.txtPassword.setText("dogsname2015")
    }

    private fun showLoader(isShown: Boolean) {
        binding.apply {
            if (isShown) {
                progressBarLoader.visibility = View.INVISIBLE
            } else {
                progressBarLoader.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}