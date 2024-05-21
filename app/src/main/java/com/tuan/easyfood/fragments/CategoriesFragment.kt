package com.tuan.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tuan.easyfood.R
import com.tuan.easyfood.activities.MainActivity
import com.tuan.easyfood.adapters.CategoryAdapter
import com.tuan.easyfood.databinding.FragmentCategoriesBinding
import com.tuan.easyfood.pojo.Category
import com.tuan.easyfood.viewModel.CategoryViewModel

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerCategories()
    }

    private fun init() {
        categoryAdapter = CategoryAdapter()
        categoryViewModel = (activity as MainActivity).categoryViewModel
    }

    private fun observerCategories() {
        categoryViewModel.getAlLCategory().observe(viewLifecycleOwner) { categories ->
            categoryAdapter.setAdapter(categories as ArrayList<Category>)
            binding.recCategories.adapter = categoryAdapter
        }
    }
}