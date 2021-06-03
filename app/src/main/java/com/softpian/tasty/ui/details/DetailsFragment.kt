package com.softpian.tasty.ui.details

import android.Manifest.permission.CALL_PHONE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.softpian.tasty.databinding.FragmentDetailsBinding
import com.softpian.tasty.models.yelp.Business
import com.softpian.tasty.models.yelp.Details
import com.softpian.tasty.utils.Constants.Companion.API_KEY
import com.softpian.tasty.utils.Constants.Companion.BUSINESS_BUNDLE_KEY
import com.softpian.tasty.utils.NetworkResult
import com.softpian.tasty.viemodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    private lateinit var intent: Intent
    private val REQUEST_CODE_CALL = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val args = arguments
        val business: Business? = args?.getParcelable(BUSINESS_BUNDLE_KEY)

        business?.let {
            getBusinessDetails(it.id)
        }

        return binding.root
    }

    private fun getBusinessDetails(businessId: String) {
        mainViewModel.getRestaurantDetails(API_KEY, businessId)
        mainViewModel.restaurantDetailsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        viewLoaded()
                        bindingData(it)
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
                    loadingView()
                }
            }
        })
    }

    private fun loadingView() {
        binding.isOpenTextView.visibility = View.INVISIBLE
        binding.ratingBar.visibility = View.INVISIBLE
        binding.reviewCountTextView.visibility = View.INVISIBLE
        binding.callImageView.visibility = View.INVISIBLE
        binding.hoursTextView.visibility = View.INVISIBLE
        binding.scrollView.visibility = View.INVISIBLE

    }

    private fun viewLoaded() {
        binding.isOpenTextView.visibility = View.VISIBLE
        binding.ratingBar.visibility = View.VISIBLE
        binding.reviewCountTextView.visibility = View.VISIBLE
        binding.callImageView.visibility = View.VISIBLE
        binding.hoursTextView.visibility = View.VISIBLE
        binding.scrollView.visibility = View.VISIBLE
    }

    private fun bindingData(details: Details) {
        binding.details = details
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        binding.callImageView.setOnClickListener {
            intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:${details.phone}")
            if (ContextCompat.checkSelfPermission(requireActivity(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent)
            } else {
                requestPermissions(arrayOf(CALL_PHONE), REQUEST_CODE_CALL)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            REQUEST_CODE_CALL -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent)
                }
            }
            else -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}