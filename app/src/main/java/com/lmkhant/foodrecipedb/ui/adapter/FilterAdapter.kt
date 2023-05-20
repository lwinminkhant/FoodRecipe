package com.lmkhant.foodrecipedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lmkhant.foodrecipedb.R
import com.lmkhant.foodrecipedb.common.Resource
import com.lmkhant.foodrecipedb.databinding.ItemFilterBinding
import com.lmkhant.foodrecipedb.model.Meal
import kotlin.random.Random

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FilterAdapter.FilterViewHolder {

        val itemFilterBinding = ItemFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return FilterViewHolder(itemFilterBinding)
    }

    override fun onBindViewHolder(holder: FilterAdapter.FilterViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
    private var onItemClickListener: ((Meal) -> Unit)? = null
    fun setOnItemClickListener(listener: (Meal) -> Unit) {
        onItemClickListener = listener
    }
    val differ = AsyncListDiffer(this, differCallback)

    inner class FilterViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.tvMealName.text = meal.strMeal
            Glide.with(binding.root)
                .load(meal.strMealThumb)
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(binding.ivFilter)

            binding.rbRating.rating = Random.nextInt(2,6).toFloat()
            binding.ivFilter.setOnClickListener {
                onItemClickListener?.let { it(meal) }
            }
        }

    }
}

