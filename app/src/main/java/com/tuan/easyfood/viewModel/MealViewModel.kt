package com.tuan.easyfood.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.tuan.easyfood.db.MealDatabase
import com.tuan.easyfood.pojo.Meal
import com.tuan.easyfood.pojo.MealList
import com.tuan.easyfood.repository.MealRepository
import com.tuan.easyfood.retrofit.MealApi
import com.tuan.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    private val mealRepository: MealRepository,
): ViewModel() {


    private var mealLiveData = MutableLiveData<Meal>()
    private var mealListLiveData = MutableLiveData<List<Meal>>()

     fun getMealDetail(id: String): LiveData<Meal> {
         mealLiveData = mealRepository.getMealDetail(id)
        return mealLiveData
    }

    fun getRandomMeal(): LiveData<Meal> {
        mealLiveData = mealRepository.getRandomMeal()
        return mealLiveData
    }

     fun upsertMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.upsertMeal(meal)
        }
    }

     fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.deleteMeal(meal)
        }
    }

    fun searchMeal(searchQuery: String): MutableLiveData<List<Meal>> {
        mealListLiveData = mealRepository.searchMeal(searchQuery)
        return mealListLiveData
    }

    fun getAllFavoriteMeal(): LiveData<List<Meal>>{
        return mealRepository.getAllFavoriteMeal()
    }
}