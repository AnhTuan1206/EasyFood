package com.tuan.easyfood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuan.easyfood.repository.CategoryRepository

class CategoryViewModelFactory(private var categoryRepository: CategoryRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(categoryRepository) as T
    }
}