package com.tuan.easyfood.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tuan.easyfood.R
import com.tuan.easyfood.activities.MainActivity
import com.tuan.easyfood.adapters.FavoritesMealAdapter
import com.tuan.easyfood.databinding.FragmentFavoritesBinding
import com.tuan.easyfood.viewModel.CategoryViewModel
import com.tuan.easyfood.viewModel.MealViewModel

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var mealViewModel: MealViewModel
    private lateinit var favoritesMealAdapter: FavoritesMealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper()
        observerFavoriteMeal()
    }

    private fun init(){
        mealViewModel = (activity as MainActivity).mealViewModel
        favoritesMealAdapter = FavoritesMealAdapter()
    }

    private fun itemTouchHelper() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val meal = favoritesMealAdapter.differ.currentList[position]
                mealViewModel.deleteMeal(meal)
                Snackbar.make(requireView(),"Meal deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        mealViewModel.upsertMeal(meal)
                    }
                ).show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recFavorites)
    }

    private fun observerFavoriteMeal() {
        mealViewModel.getAllFavoriteMeal().observe(viewLifecycleOwner) { meals ->
            favoritesMealAdapter.differ.submitList(meals)
            binding.recFavorites.adapter = favoritesMealAdapter
        }
    }
}