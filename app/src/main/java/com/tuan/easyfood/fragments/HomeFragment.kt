package com.tuan.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tuan.easyfood.activities.MealActivity
import com.tuan.easyfood.adapters.CategoryAdapter
import com.tuan.easyfood.adapters.MostPopularAdapter
import com.tuan.easyfood.constant.Constants.Companion.MEAL_ID
import com.tuan.easyfood.constant.Constants.Companion.MEAL_NAME
import com.tuan.easyfood.constant.Constants.Companion.MEAL_THUMB
import com.tuan.easyfood.databinding.FragmentHomeBinding
import com.tuan.easyfood.pojo.Category
import com.tuan.easyfood.pojo.CategoryMeal
import com.tuan.easyfood.pojo.Meal
import com.tuan.easyfood.viewModel.CategoryViewModel
import com.tuan.easyfood.viewModel.MealViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var randomMeal: Meal
    private lateinit var mealViewModel: MealViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var popularAdapter: MostPopularAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mealViewModel = ViewModelProvider(this)[MealViewModel::class.java]
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularAdapter = MostPopularAdapter()
        categoryAdapter = CategoryAdapter()
        observerMeal()
        observerCategory()
        eventClick()
    }

    private fun observerMeal() {
        mealViewModel.getRandomMeal().observe(viewLifecycleOwner) {
            meal ->
            Glide.with(this@HomeFragment).load(meal.strMealThumb).into(binding.imgRandomMeal)
            randomMeal = meal
        }

        mealViewModel.getAllMealInCategory().observe(viewLifecycleOwner) {
            mealInCategory ->
            popularAdapter.setAdapter(mealInCategory as ArrayList<CategoryMeal>)
            binding.recViewMealsPopular.adapter = popularAdapter
        }
    }


    private fun observerCategory() {
        categoryViewModel.getAlLCategory().observe(viewLifecycleOwner) {
            category ->
            categoryAdapter.setAdapter(category as ArrayList<Category>)
            binding.recViewCategories.adapter = categoryAdapter
        }
    }

    private fun eventClick(){
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java).apply {
                putExtra(MEAL_ID, randomMeal.idMeal)
                putExtra(MEAL_NAME, randomMeal.strMeal)
                putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            }
            startActivity(intent)
        }

        popularAdapter.mealClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java).apply {
                putExtra(MEAL_ID, meal.idMeal)
                putExtra(MEAL_NAME, meal.strMeal)
                putExtra(MEAL_THUMB, meal.strMealThumb)
            }
            startActivity(intent)
        }
    }


}