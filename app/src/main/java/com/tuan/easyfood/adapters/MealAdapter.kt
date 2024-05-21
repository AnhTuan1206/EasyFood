package com.tuan.easyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuan.easyfood.databinding.MealItemBinding
import com.tuan.easyfood.pojo.CategoryMeal


class MealAdapter(): RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private lateinit var categoryMealList: ArrayList<CategoryMeal>
    var onItemClick: ((CategoryMeal) -> Unit)? = null
    fun setAdapter(categoryMealList: ArrayList<CategoryMeal>) {
        this.categoryMealList = categoryMealList
        notifyDataSetChanged()
    }
    class MealViewHolder(private var binding: MealItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(itemView: View, url: String, categoryMeal: CategoryMeal) {
            Glide.with(itemView).load(url).into(binding.imgMeal)
            binding.tvMealName.text = categoryMeal.strMeal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(MealItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return categoryMealList.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(holder.itemView, categoryMealList[position].strMealThumb.toString(), categoryMealList[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(categoryMealList[position])
        }
    }
}