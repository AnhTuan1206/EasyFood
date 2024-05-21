package com.tuan.easyfood.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuan.easyfood.R
import com.tuan.easyfood.databinding.ActivityMainBinding
import com.tuan.easyfood.db.MealDatabase
import com.tuan.easyfood.repository.CategoryRepository
import com.tuan.easyfood.repository.MealRepository
import com.tuan.easyfood.viewModel.CategoryViewModel
import com.tuan.easyfood.viewModel.CategoryViewModelFactory
import com.tuan.easyfood.viewModel.MealViewModel
import com.tuan.easyfood.viewModel.MealViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val mealViewModel: MealViewModel by lazy {
        val mealRepository = MealRepository(MealDatabase.invoke(this))
        val mealViewModelFactory = MealViewModelFactory(mealRepository)
        ViewModelProvider(this, mealViewModelFactory)[MealViewModel::class.java]
    }

    val categoryViewModel: CategoryViewModel by lazy {
        val categoryRepository = CategoryRepository()
        val categoryViewModelFactory = CategoryViewModelFactory(categoryRepository)
        ViewModelProvider(this, categoryViewModelFactory)[CategoryViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }

}