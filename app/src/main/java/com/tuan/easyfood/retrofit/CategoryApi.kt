package com.tuan.easyfood.retrofit


import com.tuan.easyfood.pojo.CategoryList
import com.tuan.easyfood.pojo.MealByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApi {

    @GET("categories.php")
    fun getAllCategory(): Call<CategoryList>

    @GET("filter.php")
    fun getAllMealInCategory(@Query("c") categoryName: String): Call<MealByCategoryList>
}