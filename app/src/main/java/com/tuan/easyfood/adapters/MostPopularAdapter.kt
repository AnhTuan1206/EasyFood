package com.tuan.easyfood.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuan.easyfood.databinding.PopularItemsBinding
import com.tuan.easyfood.pojo.CategoryMeal
import com.tuan.easyfood.pojo.MealByCategoryList
import com.tuan.easyfood.pojo.MealList
import java.net.URL

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    var mealClick: ((CategoryMeal) -> Unit)? = null
    var onLongItemClick: ((CategoryMeal) -> Unit)? = null
    private lateinit var mealList: ArrayList<CategoryMeal>

    fun setAdapter(mealList: ArrayList<CategoryMeal>) {
        this.mealList = mealList
        notifyDataSetChanged()
    }

    class PopularMealViewHolder(private val binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemView: View, url: String) {
            Glide.with(itemView).load(url).into(binding.imgPopularMealItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        holder.bind(holder.itemView, mealList[position].strMealThumb.toString())
        holder.itemView.setOnClickListener {
            mealClick?.invoke(mealList[position])
        }
        holder.itemView.setOnLongClickListener {
            onLongItemClick?.invoke(mealList[position])
            true
        }
    }
}