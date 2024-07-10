package com.srm.androidtendable.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.srm.androidtendable.databinding.FragmentHomeBinding
import com.srm.androidtendable.model.Category
import com.srm.androidtendable.model.InspectionResponse
import com.srm.androidtendable.model.Question
import com.srm.androidtendable.utils.DataStatus
import com.srm.androidtendable.utils.DialogUtil
import com.srm.androidtendable.utils.UIState
import com.srm.androidtendable.viewmodel.HomeViewModel
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    private var result: InspectionResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getInspections()

        viewModel.inspectionsResult.observe(viewLifecycleOwner) {
            when (it.status) {
                DataStatus.Status.LOADING -> {
                    showLoader(false)
                }

                DataStatus.Status.SUCCESS -> {
                    it.data?.let { res ->
                        result = res
                        Log.i("SRM", "B: $result")
                        showLoader(true)
                        initRecyclerView(res.inspection.survey.categories)
                    }
                }

                DataStatus.Status.ERROR -> {
                    showLoader(true)
                    Toast.makeText(
                        context,
                        "There is something wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.submitResult.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Loading -> {
                    showLoader(false)
                }

                is UIState.Success -> {
                    showLoader(true)
                    DialogUtil.showDialog(
                        requireContext(),
                        "Result submitted successfully !!!"
                    )
                }

                is UIState.Error -> {
                    showLoader(true)
                    Toast.makeText(
                        context,
                        "There is something wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else -> { }
            }
        }

        binding.btnSubmit.setOnClickListener {
            showLoader(false)
            if (result == null) {
                Toast.makeText(
                    context,
                    "Please select all the options",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.submitInspections(result!!)
            }
        }
    }

    private fun initRecyclerView(categories: List<Category>) {
        val adapter = QuestionAdapter(categories, object : QuestionAdapter.OnItemSelection {
            override fun onItemClick(question: List<Question>) {
                result?.inspection?.survey?.categories = categories
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun showLoader(isShown: Boolean) {
        binding.apply {
            if (isShown) {
                progressBarLoader.visibility = View.GONE
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