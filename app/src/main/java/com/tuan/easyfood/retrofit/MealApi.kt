package com.tuan.easyfood.retrofit

import com.tuan.easyfood.pojo.MealByCategoryList
import com.tuan.easyfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php")
    fun getMealDetail(@Query("i") id: String):Call<MealList>

    @GET("filter.php")
    fun getAllMealInCategory(@Query("c") categoryName: String): Call<MealByCategoryList>

}