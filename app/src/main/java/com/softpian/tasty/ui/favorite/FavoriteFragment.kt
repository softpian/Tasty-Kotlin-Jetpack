package com.softpian.tasty.ui.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.softpian.tasty.R
import com.softpian.tasty.adapters.FavoriteAdapter
import com.softpian.tasty.data.database.entities.TastyFavoriteEntity
import com.softpian.tasty.databinding.FragmentFavoriteBinding
import com.softpian.tasty.viemodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val adapter by lazy { FavoriteAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        showNoFavoriteIcons(false)

        setupRecyclerView()
        getFavorites()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.favoriteRecyclerView.adapter = adapter
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getFavorites() {
        mainViewModel.readFavorites.observe(requireActivity(), { tastyFavoriteEntity ->
            if (tastyFavoriteEntity.isNotEmpty()) {
                showNoFavoriteIcons(false)
                adapter.setData(tastyFavoriteEntity)
            } else {
                adapter.setData(emptyList())
                showNoFavoriteIcons(true)
            }
        })
    }

    private fun showNoFavoriteIcons(isVisible: Boolean) {
        if (isVisible) {
            binding.noFavoriteImageView.visibility = View.VISIBLE
            binding.noFavoriteTextView.visibility = View.VISIBLE
        } else {
            binding.noFavoriteImageView.visibility = View.INVISIBLE
            binding.noFavoriteTextView.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}