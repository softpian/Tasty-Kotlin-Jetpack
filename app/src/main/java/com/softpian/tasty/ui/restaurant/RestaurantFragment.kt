package com.softpian.tasty.ui.restaurant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.softpian.tasty.R
import com.softpian.tasty.adapters.RestaurantAdapter
import com.softpian.tasty.databinding.FragmentRestaurantBinding
import com.softpian.tasty.utils.Constants.Companion.API_KEY
import com.softpian.tasty.utils.Constants.Companion.DEFAULT_FOOD_TYPE
import com.softpian.tasty.utils.Constants.Companion.DEFAULT_LOCATION
import com.softpian.tasty.utils.Constants.Companion.LOCATION
import com.softpian.tasty.utils.Constants.Companion.TERM
import com.softpian.tasty.utils.NetworkResult
import com.softpian.tasty.viemodels.MainViewModel
import com.softpian.tasty.viemodels.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantFragment : Fragment() {

    private val args by navArgs<RestaurantFragmentArgs>()

    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { RestaurantAdapter() }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var restaurantViewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        restaurantViewModel = ViewModelProvider(requireActivity())[RestaurantViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)

        setupRecyclerView()
        getRestaurants()

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_restaurantFragment_to_restaurantBottomSheet)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getRestaurants() {
        if (!args.isFromBottomSheet) {
            loadDatabase()
        } else {
            requestApi()
        }
    }

    private fun loadDatabase() {
        mainViewModel.readTastyRestaurants.observe(viewLifecycleOwner, { databaseTable ->
            if (databaseTable.isNotEmpty()) {
                hideShimmer()
                adapter.setData(databaseTable[0].tastyRestaurant.businesses)
            } else {
                requestApi()
            }
        })
    }

    private fun requestApi() {
        mainViewModel.getRestaurants(API_KEY, restaurantViewModel.buildQueries())
        mainViewModel.restaurantResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    response.data?.let {
                        adapter.setData(it.businesses)
                    }
                }
                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDatabase();
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmer()
                }
            }
        })
    }

    private fun showShimmer() {
        binding.recyclerView.showShimmer()
    }

    private fun hideShimmer() {
        binding.recyclerView.hideShimmer()
    }

    private fun buildQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[TERM] = DEFAULT_FOOD_TYPE
        queries[LOCATION] = DEFAULT_LOCATION

        return queries
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}