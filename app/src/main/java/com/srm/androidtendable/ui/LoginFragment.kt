package com.srm.androidtendable.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.srm.androidtendable.R
import com.srm.androidtendable.databinding.FragmentLoginBinding
import com.srm.androidtendable.utils.DialogUtil
import com.srm.androidtendable.utils.UIState
import com.srm.androidtendable.viewmodel.LoginViewModel
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoader(true)
        initData()
        viewModel.loginUserResult.observe(viewLifecycleOwner) {
            when(it) {
                is UIState.Loading -> {
                    showLoader(false)
                }
                is UIState.Success -> {
                    showLoader(true)
                    Toast.makeText(
                        context,
                        "Registration successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is UIState.Error -> {
                    showLoader(true)
                    DialogUtil.showDialog(
                        requireContext(),
                        "Timeout, user not found, please register using Sign up first"
                    )
                } else -> {}
            }
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login()
        }

        binding.btnSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
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