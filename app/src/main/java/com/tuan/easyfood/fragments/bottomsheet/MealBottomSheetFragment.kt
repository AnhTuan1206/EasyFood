package com.tuan.easyfood.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tuan.easyfood.activities.MainActivity
import com.tuan.easyfood.activities.MealActivity
import com.tuan.easyfood.constant.Constants.Companion.MEAL_ID
import com.tuan.easyfood.constant.Constants.Companion.MEAL_NAME
import com.tuan.easyfood.constant.Constants.Companion.MEAL_THUMB
import com.tuan.easyfood.databinding.FragmentMealBottomSheetBinding
import com.tuan.easyfood.viewModel.MealViewModel

private const val MEAL_ID = "param1"

class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var mealId: String
    private lateinit var mealViewModel: MealViewModel
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        arguments?.let {
            mealId = it.getString(MEAL_ID).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMealById(mealId)
        eventClick()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }

    private fun init() {
        mealViewModel = (activity as MainActivity).mealViewModel
    }

    private fun getMealById(id: String){
        mealViewModel.getMealDetail(id).observe(viewLifecycleOwner) { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgBottomSheet)
            binding.tvBottomSheetArea.text = meal.strArea
            binding.tvBottomSheetCategory.text = meal.strCategory
            binding.tvBottomSheetMealName.text = meal.strMeal
            mealName = meal.strMeal.toString()
            mealThumb = meal.strMealThumb.toString()
        }
    }

    private fun eventClick() {
        binding.bottomSheet.setOnClickListener {
            if(mealName != null && mealThumb != null) {
                val intent = Intent(activity, MealActivity::class.java).apply {
                    putExtra(MEAL_NAME, mealName)
                    putExtra(MEAL_THUMB, mealThumb)
                    putExtra(MEAL_ID, mealId)
                }
                startActivity(intent)
            }

        }
    }
}