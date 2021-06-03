package com.softpian.tasty.ui.restaurant.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.softpian.tasty.databinding.RestaurantBottomSheetBinding
import com.softpian.tasty.utils.Constants.Companion.DEFAULT_FOOD_TYPE
import com.softpian.tasty.utils.Constants.Companion.DEFAULT_LOCATION
import com.softpian.tasty.viemodels.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantBottomSheet : BottomSheetDialogFragment() {

    private var _binding: RestaurantBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val restaurantViewModel by viewModels<RestaurantViewModel>()

    private var checkedLocation = DEFAULT_LOCATION
    private var checkedLocationId = 0
    private var checkedFoodType = DEFAULT_FOOD_TYPE
    private var checkedFoodTypeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = RestaurantBottomSheetBinding.inflate(inflater, container, false)

        restaurantViewModel.checkedChipsFlow.asLiveData().observe(viewLifecycleOwner, Observer { checkedChip ->
            checkedLocation = checkedChip.checkedLocation
            checkedLocationId = checkedChip.checkedLocationId
            checkedFoodType = checkedChip.checkedFoodType
            checkedFoodTypeId = checkedChip.checkedFoodTypeId
            updateCheckedChip(checkedLocationId, binding.locationChipGroup)
            updateCheckedChip(checkedFoodTypeId, binding.foodTypeChipGroup)
        })

        binding.locationChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            checkedLocation = chip.text.toString()
            checkedLocationId = checkedId
        }

        binding.foodTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            checkedFoodType = chip.text.toString()
            checkedFoodTypeId = checkedId
        }

        binding.searchButton.setOnClickListener {
            restaurantViewModel.writeCheckedChips(
                checkedLocation,
                checkedLocationId,
                checkedFoodType,
                checkedFoodTypeId
            )

            val navDirections = RestaurantBottomSheetDirections.actionRestaurantBottomSheetToRestaurantFragment(true)
            findNavController().navigate(navDirections)
        }

        return binding.root
    }

    private fun updateCheckedChip(checkedId: Int, chipGroup: ChipGroup) {
        if (checkedId != 0) {
            val chip = chipGroup.findViewById<Chip>(checkedId)
            chip.isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}