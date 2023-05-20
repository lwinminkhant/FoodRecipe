package com.lmkhant.foodrecipedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lmkhant.foodrecipedb.R
import com.lmkhant.foodrecipedb.common.Resource
import com.lmkhant.foodrecipedb.databinding.FragmentHomeBinding
import com.lmkhant.foodrecipedb.model.FilterQuery
import com.lmkhant.foodrecipedb.ui.adapter.ListAdapter
import com.lmkhant.foodrecipedb.ui.adapter.CategoryAdapter
import com.lmkhant.foodrecipedb.viewmodel.HomeViewModel
import com.lmkhant.foodrecipedb.viewmodel.FilterViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var areaListAdapter: ListAdapter
    private lateinit var ingredientListAdapter: ListAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        setupCategory()
        setupArea()
        setupIngredient()


        return root
    }

    private fun setupCategory(){
        categoryAdapter = CategoryAdapter()
        viewModel.getCategories()
        binding.rvRecipeCategories.layoutManager = LinearLayoutManager(requireContext().applicationContext, LinearLayoutManager.HORIZONTAL,false)
        binding.rvRecipeCategories.adapter = categoryAdapter

        categoryAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("Filter", FilterQuery(queryType = "c", query = it.strCategory))
            }
            findNavController().navigate(
                R.id.action_navigation_home_to_filter_fragment,
                bundle
            )
        }


        viewModel.categories.observe(requireActivity()) { response ->
            when(response){
                is Resource.Success->{
                    binding.loading.visibility = View.GONE
                    response.data?.let{
                        categoryAdapter.differ.submitList(it.categories.toList())
                    }
                }
                is Resource.Error -> {
                    binding.loading.visibility = View.GONE
                    response.message?.let {
                        Toast.makeText(requireContext(),"Error: $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun setupArea(){
        areaListAdapter = ListAdapter()
        viewModel.getMealsByArea()
        binding.rvRecipeArea.layoutManager = LinearLayoutManager(requireContext().applicationContext, LinearLayoutManager.HORIZONTAL,false)
        binding.rvRecipeArea.adapter = areaListAdapter

        areaListAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("Filter",
                    it.strArea?.let { it1 -> FilterQuery(queryType = "a", query = it1) })
            }

            findNavController().navigate(
                R.id.action_navigation_home_to_filter_fragment,
                bundle
            )
        }

        viewModel.areas.observe(requireActivity()) { response ->
            when(response){
                is Resource.Success->{
                    binding.loading.visibility = View.GONE
                    response.data?.let{
                        areaListAdapter.differ.submitList(it.meals?.toList())
                    }
                }
                is Resource.Error -> {
                    binding.loading.visibility = View.GONE
                    response.message?.let {
                        Toast.makeText(requireContext(),"Error: $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun setupIngredient(){
        ingredientListAdapter = ListAdapter()
        viewModel.getMealsByIngredient()

        binding.rvRecipeIngredient.layoutManager = LinearLayoutManager(requireContext().applicationContext, LinearLayoutManager.HORIZONTAL,false)
        binding.rvRecipeIngredient.adapter = ingredientListAdapter

        ingredientListAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("Filter",
                    it.strIngredient?.let { it1 -> FilterQuery(queryType = "i", query = it1) })
            }
            findNavController().navigate(
                R.id.action_navigation_home_to_filter_fragment,
                bundle
            )
        }

        viewModel.ingredients.observe(requireActivity()) { response ->
            when(response){
                is Resource.Success->{
                    binding.loading.visibility = View.GONE
                    response.data?.let{
                        ingredientListAdapter.differ.submitList(it.meals?.toList())
                    }
                }
                is Resource.Error -> {
                    binding.loading.visibility = View.GONE
                    response.message?.let {
                        Toast.makeText(requireContext(),"Error: $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}