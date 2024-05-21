package com.tuan.easyfood.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tuan.easyfood.pojo.Category
import com.tuan.easyfood.pojo.CategoryList
import com.tuan.easyfood.pojo.CategoryMeal
import com.tuan.easyfood.pojo.MealByCategoryList
import com.tuan.easyfood.retrofit.CategoryApi
import com.tuan.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryRepository {

    private var categoryApi = RetrofitInstance.api().create(CategoryApi::class.java)
    private var categoryLiveData = MutableLiveData<List<Category>>()
    private var categoryMealLiveData = MutableLiveData<List<CategoryMeal>>()

    fun getAlLCategory(): MutableLiveData<List<Category>> {
        categoryApi.getAllCategory().enqueue(object : Callback<CategoryList> {
            override fun onResponse(p0: Call<CategoryList>, p1: Response<CategoryList>) {
                if(p1.body() != null){
                    categoryLiveData.value = p1.body()!!.categories!!
                }else return
            }

            override fun onFailure(p0: Call<CategoryList>, p1: Throwable) {
                Log.e("Error getAlLCategory", p1.message.toString())
            }
        })
        return categoryLiveData
    }

    fun getAllMealInCategorySeafood(categoryName: String = "Seafood"): MutableLiveData<List<CategoryMeal>> {
        categoryApi.getAllMealInCategory(categoryName).enqueue(object :
            Callback<MealByCategoryList> {
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if (response.body() != null) {
                    categoryMealLiveData.value = response.body()!!.meals!!
                } else return
            }

            override fun onFailure(call: Call<MealByCategoryList>, response: Throwable) {
                Log.e("Error getAllMealInCategorySeafood", response.message.toString())
            }

        })
        return categoryMealLiveData
    }

    fun getAllMealInCategory(categoryName: String): MutableLiveData<List<CategoryMeal>> {
        categoryApi.getAllMealInCategory(categoryName).enqueue(object :
            Callback<MealByCategoryList> {
            override fun onResponse(
                p0: Call<MealByCategoryList>,
                p1: Response<MealByCategoryList>
            ) {
                if(p1.body() != null) {
                    categoryMealLiveData.value = p1.body()!!.meals!!
                }else return
            }

            override fun onFailure(p0: Call<MealByCategoryList>, p1: Throwable) {
                Log.e("Error getAllMealInCategory", p1.message.toString())
            }

        })
        return categoryMealLiveData
    }
}