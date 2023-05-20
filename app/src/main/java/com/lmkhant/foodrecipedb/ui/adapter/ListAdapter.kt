package com.lmkhant.foodrecipedb.ui.adapter

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lmkhant.foodrecipedb.databinding.ItemAreaBinding
import com.lmkhant.foodrecipedb.model.Meal


class ListAdapter : RecyclerView.Adapter<ListAdapter.ListItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val itemAreaBinding = ItemAreaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ListItemViewHolder(itemAreaBinding)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ListItemViewHolder(val binding: ItemAreaBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(meal: Meal) {
            if (meal.strArea != null)
                binding.tvListItem.text = meal.strArea

            if (meal.strIngredient != null)
                binding.tvListItem.text = meal.strIngredient

            val v = binding.tvListItem.height
            val h: Int = v
            val mDrawable = ShapeDrawable(RectShape())
            mDrawable.paint.shader = LinearGradient(
                0f,
                0f,
                0f,
                h.toFloat(),
                getRandomColor(),
                getRandomColor(),
                Shader.TileMode.REPEAT
            )

            binding.tvListItem.background = mDrawable
            binding.tvListItem.setOnClickListener {
                onItemClickListener?.let { it(meal) }
            }
        }
    }

    fun getRandomColor(): Int {
        val rnd = kotlin.random.Random
        return Color.argb(200, rnd.nextInt(150,255), rnd.nextInt(150,255), rnd.nextInt(150,255))
    }
    private var onItemClickListener :((Meal)->Unit)?= null

    fun setOnItemClickListener(listener: (Meal)->Unit){
        onItemClickListener = listener
    }


    private val differCallback = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}