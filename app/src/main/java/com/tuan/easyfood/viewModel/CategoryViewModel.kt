package com.tuan.easyfood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuan.easyfood.pojo.Category
import com.tuan.easyfood.pojo.CategoryList
import com.tuan.easyfood.retrofit.CategoryApi
import com.tuan.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel(private var categoryApi: CategoryApi = RetrofitInstance.api().create(CategoryApi::class.java)): ViewModel() {

    private var categoryLiveData = MutableLiveData<List<Category>>()

    fun getAlLCategory(): LiveData<List<Category>> {
       categoryApi.getAllCategory().enqueue(object : Callback<CategoryList> {
           override fun onResponse(p0: Call<CategoryList>, p1: Response<CategoryList>) {
               if(p1.body() != null){
                   categoryLiveData.value = p1.body()!!.categories!!
               }else return
           }

           override fun onFailure(p0: Call<CategoryList>, p1: Throwable) {
               return
           }
       })
        return categoryLiveData
    }
}