package com.tuan.easyfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuan.easyfood.databinding.CategoryItemBinding
import com.tuan.easyfood.pojo.Category

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var categoryList: ArrayList<Category>
    var onItemClick: ((Category) -> Unit)? = null

    fun setAdapter(categoryList: ArrayList<Category>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    class CategoryViewHolder(private val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(itemView: View, url: String, category: Category) {
            Glide.with(itemView).load(url).into(binding.imgCategory)
            binding.tvCategoryName.text = category.strCategory
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(holder.itemView, categoryList[position].strCategoryThumb.toString(), categoryList[position])
        holder.itemView.setOnClickListener { onItemClick!!.invoke(categoryList[position]) }
    }
}