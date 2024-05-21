package com.tuan.easyfood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuan.easyfood.pojo.Category
import com.tuan.easyfood.pojo.CategoryList
import com.tuan.easyfood.pojo.CategoryMeal
import com.tuan.easyfood.pojo.MealByCategoryList
import com.tuan.easyfood.repository.CategoryRepository
import com.tuan.easyfood.retrofit.CategoryApi
import com.tuan.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel(private var categoryRepository: CategoryRepository): ViewModel() {

    private var categoryLiveData = MutableLiveData<List<Category>>()
    private var categoryMealLiveData = MutableLiveData<List<CategoryMeal>>()

    fun getAlLCategory(): MutableLiveData<List<Category>> {
        categoryLiveData = categoryRepository.getAlLCategory()
        return categoryLiveData
    }

    fun getAllMealInCategorySeafood(categoryName: String = "Seafood"): MutableLiveData<List<CategoryMeal>> {
        categoryMealLiveData = categoryRepository.getAllMealInCategorySeafood(categoryName)
        return categoryMealLiveData
    }

    fun getAllMealInCategory(categoryName: String): MutableLiveData<List<CategoryMeal>> {
        categoryMealLiveData = categoryRepository.getAllMealInCategory(categoryName)
        return categoryMealLiveData
    }
}