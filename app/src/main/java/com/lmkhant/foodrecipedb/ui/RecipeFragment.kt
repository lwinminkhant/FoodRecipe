package com.lmkhant.foodrecipedb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.lmkhant.foodrecipedb.common.Resource
import com.lmkhant.foodrecipedb.databinding.FragmentRecipeBinding
import com.lmkhant.foodrecipedb.model.Meal
import com.lmkhant.foodrecipedb.viewmodel.RecipeViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val viewModel: RecipeViewModel by activityViewModels()
    private val args:RecipeFragmentArgs by navArgs()
    private val binding get() = _binding!!
    private lateinit var youtubePlayerView: YouTubePlayerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater,container,false)

        val id=args.recipeId
        viewModel.getMealById(id)

        viewModel.meals.observe(viewLifecycleOwner){response ->
            when (response) {
                is Resource.Success -> {
                    binding.loading.visibility = View.INVISIBLE
                    response.data?.let {
                        setRecipe(it.meals[0])
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

        binding.ivFavourite.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        return binding.root
    }

    private fun setRecipe(meal: Meal?) {
        Glide.with(binding.root)
            .load(meal?.strMealThumb)
            .into(binding.ivRecipe)

        binding.tvRecipeName.text = meal?.strMeal

        binding.tvIngredient.text = meal?.let { viewModel.getIngredients(it) }

        binding.tvInstruction.text = meal?.strInstructions

        youtubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youtubePlayerView)

        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = meal?.strYoutube?.let { viewModel.getYouTubeId(it) }
                if (videoId != null) {
                    youTubePlayer.cueVideo(videoId, 0f)
                }

            }
        })


    }
}