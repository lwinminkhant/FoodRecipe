package com.lmkhant.foodrecipedb.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lmkhant.foodrecipedb.R
import com.lmkhant.foodrecipedb.common.Resource
import com.lmkhant.foodrecipedb.databinding.FragmentFilterBinding
import com.lmkhant.foodrecipedb.ui.adapter.FilterAdapter
import com.lmkhant.foodrecipedb.viewmodel.FilterViewModel

class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val args:FilterFragmentArgs by navArgs()
    private val viewModel: FilterViewModel by activityViewModels()
    private lateinit var filterAdapter: FilterAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container,false)

        val filter=args.filter
        val root: View = binding.root
        filterAdapter = FilterAdapter()
        binding.rvFilter.adapter = filterAdapter
        binding.rvFilter.layoutManager = LinearLayoutManager(requireContext().applicationContext,LinearLayoutManager.VERTICAL,false)
        filterAdapter.differ.submitList(emptyList())

        when(filter.queryType){
            "a"->{
                viewModel.getFilterByArea(filter.query)
            }
            "c"->{
                viewModel.getFilterByCategory(filter.query)
            }
            "i"->{
                viewModel.getFilterByIngredient(filter.query)
            }
        }
        filterAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putString("RecipeId", it.idMeal)
            }
            findNavController().navigate(
                R.id.action_filter_fragment_to_recipeFragment,
                bundle
            )
        }

        viewModel.filters.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.loading.visibility = View.INVISIBLE
                    response.data?.let {
                        filterAdapter.differ.submitList(it.meals?.toList())
                    }
                }

                is Resource.Error -> {
                    binding.loading.visibility = View.INVISIBLE
                    response.message?.let {
                        Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
            }
        }

        return root
    }

}