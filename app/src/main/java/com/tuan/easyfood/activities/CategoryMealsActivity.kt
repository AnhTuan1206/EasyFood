package com.tuan.easyfood.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tuan.easyfood.R
import com.tuan.easyfood.adapters.MealAdapter
import com.tuan.easyfood.constant.Constants
import com.tuan.easyfood.databinding.ActivityCategoryMealsBinding
import com.tuan.easyfood.pojo.CategoryMeal
import com.tuan.easyfood.repository.CategoryRepository
import com.tuan.easyfood.viewModel.CategoryViewModel
import com.tuan.easyfood.viewModel.CategoryViewModelFactory

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryName: String
    private lateinit var mealAdapter: MealAdapter
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var categoryViewModelFactory: CategoryViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryRepository = CategoryRepository()
        categoryViewModelFactory = CategoryViewModelFactory(categoryRepository)
        categoryViewModel = ViewModelProvider(this, categoryViewModelFactory)[CategoryViewModel::class.java]
        mealAdapter = MealAdapter()
        getInformationCategory()
        getMealByCategory()
        eventClick()
    }

    private fun getInformationCategory() {
        categoryName = intent.getStringExtra(Constants.CATEGORY_NAME)!!
    }

    private fun getMealByCategory() {
        categoryViewModel.getAllMealInCategory(categoryName).observe(this) { mealList ->
            mealAdapter.setAdapter(mealList as ArrayList<CategoryMeal>)
            binding.tvCategoryCount.text = mealList.size.toString()
            binding.recMeals.adapter = mealAdapter
        }
    }

    private fun eventClick() {
        mealAdapter.onItemClick = { meal ->
            val intent = Intent(this, MealActivity::class.java).apply {
                putExtra(Constants.MEAL_ID, meal.idMeal)
                putExtra(Constants.MEAL_NAME, meal.strMeal)
                putExtra(Constants.MEAL_THUMB, meal.strMealThumb)
            }
            startActivity(intent)
        }
    }
}