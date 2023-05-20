package com.lmkhant.foodrecipedb.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lmkhant.foodrecipedb.R
import com.lmkhant.foodrecipedb.common.Resource
import com.lmkhant.foodrecipedb.databinding.FragmentSearchBinding
import com.lmkhant.foodrecipedb.model.Meal
import com.lmkhant.foodrecipedb.ui.adapter.FilterAdapter
import com.lmkhant.foodrecipedb.viewmodel.FilterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val viewModel: FilterViewModel by activityViewModels()
    private lateinit var filterAdapter: FilterAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val searchEditText = binding.etSearch
        val s2 = binding.svSearch
        s2.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {

                private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

                private var searchJob: Job? = null

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchJob?.cancel()
                    searchJob = coroutineScope.launch {
                        newText?.let {
                            delay(500)
                            if (it.isNotEmpty()) {
                                viewModel.getFilterBySearch(it)
                            }else{
                                viewModel.filters.postValue(null)
                            }
                        }
                    }
                    return false
                }
            }
        )

        filterAdapter = FilterAdapter()
        binding.rvFilter.adapter = filterAdapter
        binding.rvFilter.layoutManager =
            LinearLayoutManager(this.requireContext(),LinearLayoutManager.VERTICAL,false)

        filterAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putString("RecipeId", it.idMeal)
            }
            findNavController().navigate(
                R.id.action_navigation_search_to_recipeFragment,
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}