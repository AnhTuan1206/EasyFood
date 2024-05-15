package com.tuan.easyfood.retrofit


import com.tuan.easyfood.pojo.CategoryList
import retrofit2.Call
import retrofit2.http.GET

interface CategoryApi {

    @GET("categories.php")
    fun getAllCategory(): Call<CategoryList>
}