package com.tuan.easyfood.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.tuan.easyfood.db.MealDatabase
import com.tuan.easyfood.pojo.Meal
import com.tuan.easyfood.pojo.MealList
import com.tuan.easyfood.retrofit.MealApi
import com.tuan.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealRepository(
    private val mealDatabase: MealDatabase
) {

    private var mealApi: MealApi = RetrofitInstance.api().create(MealApi::class.java)
    private var mealLiveData = MutableLiveData<Meal>()
    private var mealListLiveData = MutableLiveData<List<Meal>>()
    private var saveStateRandomMeal: Meal ?= null

    fun getMealDetail(id: String): MutableLiveData<Meal> {
        mealApi.getMealDetail(id).enqueue(object : Callback<MealList> {
            override fun onResponse(p0: Call<MealList>, p1: Response<MealList>) {
                if (p1.body() != null) {
                    mealLiveData.value = p1.body()!!.meals?.get(0)
                } else return
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.e("Error getMealDetail", p1.message.toString())
            }

        })
        return mealLiveData
    }

    fun getRandomMeal(): MutableLiveData<Meal> {
        mealApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(p0: Call<MealList>, p1: Response<MealList>) {
                if (p1.body() != null) {
                    mealLiveData.value = p1.body()!!.meals?.get(0)
                    saveStateRandomMeal = p1.body()!!.meals?.get(0)
                } else return
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.e("Error getRandomMeal", p1.message.toString())
            }

        })
        return mealLiveData
    }

    fun searchMeal(searchQuery: String): MutableLiveData<List<Meal>>{
        mealApi.searchMeal(searchQuery).enqueue(object : Callback<MealList> {
            override fun onResponse(p0: Call<MealList>, p1: Response<MealList>) {
                if(p1.body() != null) {
                    mealListLiveData.value = p1.body()!!.meals as List<Meal>?
                }else return
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.e("Error searchMeal", p1.message.toString())
            }
        })
        return mealListLiveData
    }

    suspend fun upsertMeal(meal: Meal) = mealDatabase.mealDao().upsert(meal)

    suspend fun deleteMeal(meal: Meal) = mealDatabase.mealDao().delete(meal)

    fun getAllFavoriteMeal() = mealDatabase.mealDao().getAllMeal()

}