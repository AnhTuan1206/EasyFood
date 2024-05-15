package com.tuan.easyfood.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

//    val api: MealApi by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(MealApi::class.java)
//    }

    fun api(): Retrofit {
        val api = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return api
    }
}