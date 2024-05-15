package com.tuan.easyfood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuan.easyfood.pojo.CategoryMeal
import com.tuan.easyfood.pojo.Meal
import com.tuan.easyfood.pojo.MealByCategoryList
import com.tuan.easyfood.pojo.MealList
import com.tuan.easyfood.retrofit.MealApi
import com.tuan.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(private var mealApi: MealApi = RetrofitInstance.api().create(MealApi::class.java)): ViewModel() {

    private var mealLiveData = MutableLiveData<Meal>()
    private var categoryMealLiveData = MutableLiveData<List<CategoryMeal>>()

    fun getMealDetail(id: String): LiveData<Meal> {
        mealApi.getMealDetail(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealLiveData.value = response.body()!!.meals?.get(0)
                } else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                return
            }
        })
        return mealLiveData
    }

    fun getRandomMeal(): LiveData<Meal> {
        mealApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealLiveData.value = response.body()!!.meals?.get(0)
                } else return
            }

            override fun onFailure(call: Call<MealList>, response: Throwable) {
                return
            }
        })
        return mealLiveData
    }

    fun getAllMealInCategory(categoryName: String = "Seafood"): LiveData<List<CategoryMeal>> {
        mealApi.getAllMealInCategory(categoryName).enqueue(object : Callback<MealByCategoryList> {
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if (response.body() != null) {
                    categoryMealLiveData.value = response.body()!!.meals!!
                } else return
            }

            override fun onFailure(call: Call<MealByCategoryList>, response: Throwable) {
                return
            }

        })
        return categoryMealLiveData

    }
}