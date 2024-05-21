package com.tuan.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.tuan.easyfood.activities.MainActivity
import com.tuan.easyfood.adapters.SearchMealAdapter
import com.tuan.easyfood.databinding.FragmentSearchBinding
import com.tuan.easyfood.viewModel.MealViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var mealViewModel: MealViewModel
    private lateinit var searchMealAdapter: SearchMealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textSearchChangedListener()
        eventClick()
    }

    private fun init() {
        mealViewModel = (activity as MainActivity).mealViewModel
        searchMealAdapter = SearchMealAdapter()
    }

    private fun eventClick() {
        binding.imgSearch.setOnClickListener {
            if(binding.etSearch.text.toString().isEmpty()){
                Toast.makeText(context, "Enter data to search", Toast.LENGTH_SHORT).show()
            }else observerSearchMeal(binding.etSearch.text.toString())
        }
    }

    private fun textSearchChangedListener() {
        var searchJob: Job? = null
        binding.etSearch.addTextChangedListener{ searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500) // delay để tránh api bị gọi liên tục, ở đây sau khi dừng nhập text 500 mili giây thì mới chạy
                observerSearchMeal(searchQuery.toString())
            }
        }
    }

    private fun observerSearchMeal(searchQuery: String) {
        mealViewModel.searchMeal(searchQuery).observe(viewLifecycleOwner) { mealList ->
            searchMealAdapter.differ.submitList(mealList)
            binding.recSearchMeal.adapter = searchMealAdapter
        }
    }


}