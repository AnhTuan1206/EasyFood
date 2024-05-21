package com.tuan.easyfood.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.PrimaryKey
import com.tuan.easyfood.db.MealDatabase
import com.tuan.easyfood.repository.MealRepository

class MealViewModelFactory(
    private val mealRepository: MealRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealRepository) as T
    }
}