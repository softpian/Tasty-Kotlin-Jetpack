package com.softpian.tasty.ui.reviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.softpian.tasty.adapters.ReviewAdapter
import com.softpian.tasty.databinding.FragmentReviewsBinding
import com.softpian.tasty.models.yelp.Business
import com.softpian.tasty.utils.Constants.Companion.API_KEY
import com.softpian.tasty.utils.Constants.Companion.BUSINESS_BUNDLE_KEY
import com.softpian.tasty.utils.NetworkResult
import com.softpian.tasty.viemodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : Fragment() {

    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { ReviewAdapter() }

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewsBinding.inflate(layoutInflater, container, false)

        setupRecyclerView()

        val args = arguments
        val business: Business? = args?.getParcelable(BUSINESS_BUNDLE_KEY)

        business?.let {
            getReviews(it.id)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.reviewsRecyclerView.adapter = adapter
        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getReviews(businessId: String) {
        mainViewModel.getRestaurantReviews(API_KEY, businessId)
        mainViewModel.restaurantReviewsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        adapter.setData(it.reviews)
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}