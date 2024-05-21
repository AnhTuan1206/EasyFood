package com.tuan.easyfood.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tuan.easyfood.R
import com.tuan.easyfood.constant.Constants.Companion.MEAL_ID
import com.tuan.easyfood.constant.Constants.Companion.MEAL_NAME
import com.tuan.easyfood.constant.Constants.Companion.MEAL_THUMB
import com.tuan.easyfood.databinding.ActivityMealBinding
import com.tuan.easyfood.db.MealDatabase
import com.tuan.easyfood.pojo.Meal
import com.tuan.easyfood.repository.MealRepository
import com.tuan.easyfood.viewModel.MealViewModel
import com.tuan.easyfood.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealLinkYoutube: String
    private lateinit var mealViewModel: MealViewModel
    private var mealToSaveFavorite: Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        getMealInformation()
        setInformationInViews()
        loadingCase()
        observerMealDetailLiveData()
        eventClick()
    }

    private fun init() {
        val mealRepository = MealRepository(MealDatabase.invoke(this))
        val mealViewModelFactory = MealViewModelFactory(mealRepository)
        mealViewModel = ViewModelProvider(this, mealViewModelFactory)[MealViewModel::class.java]
    }

    private fun getMealInformation() {
        intent.apply {
            mealId = this.getStringExtra(MEAL_ID)!!
            mealThumb = this.getStringExtra(MEAL_THUMB)!!
            mealName = this.getStringExtra(MEAL_NAME)!!
        }
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealDetail)
        binding.collapsingToolbar.apply {
            title = mealName
            setCollapsedTitleTextColor(resources.getColor(R.color.white))
            setExpandedTitleColor(resources.getColor(R.color.white))
        }
    }

    private fun observerMealDetailLiveData() {
        mealViewModel.getMealDetail(mealId).observe(this
        ) { value ->
            onResponseCase()
            binding.tvArea.text = "Area: ${value.strArea}"
            binding.tvCategories.text = "Category: ${value.strCategory}"
            binding.tvInstructions.text = "- Instructions: ${value.strInstructions}"
            mealLinkYoutube = value.strYoutube.toString()
            mealToSaveFavorite = value
        }
    }

    private fun eventClick() {
        binding.imgYoutube.setOnClickListener {
            onToYoutubeImgClick()
        }

        binding.btnFavorite.setOnClickListener {
            onFavoriteClick()
        }
    }

    private fun onToYoutubeImgClick() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mealLinkYoutube)))
    }

    private fun onFavoriteClick() {
        mealToSaveFavorite?.let { mealViewModel.upsertMeal(it) }
        Toast.makeText(this,"Meal saved", Toast.LENGTH_SHORT).show()
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnFavorite.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvCategories.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnFavorite.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvCategories.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }

}