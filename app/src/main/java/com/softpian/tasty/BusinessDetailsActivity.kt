package com.softpian.tasty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.softpian.tasty.adapters.ViewPagerAdapter
import com.softpian.tasty.databinding.ActivityBusinessDetailsBinding
import com.softpian.tasty.ui.details.DetailsFragment
import com.softpian.tasty.ui.reviews.ReviewsFragment
import com.softpian.tasty.ui.webpage.WebPageFragment
import com.softpian.tasty.utils.Constants.Companion.BUSINESS_BUNDLE_KEY
import com.softpian.tasty.viemodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusinessDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusinessDetailsBinding
    private val args by navArgs<BusinessDetailsActivityArgs>()
    private val mainViewModel by viewModels<MainViewModel>()

    private var wasSaved = false
    private var savedId = 0
    private var menuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = arrayListOf(
            DetailsFragment(),
            ReviewsFragment(),
            WebPageFragment()
        )

        val titles = arrayListOf(
            "Details",
            "Reviews",
            "Web Page"
        )

        val businessBundle = Bundle()
        businessBundle.putParcelable(BUSINESS_BUNDLE_KEY, args.business)

        val viewPagerAdapter = ViewPagerAdapter(
            businessBundle,
            fragments,
            this
        )

        binding.viewPager.run {
            adapter = viewPagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        menuItem = menu?.findItem(R.id.save_restaurant_to_favorites)
        checkSavedFavorite(menuItem)
        return super.onCreateOptionsMenu(menu)
    }

    private fun checkSavedFavorite(item: MenuItem?) {
        mainViewModel.readFavorites.observe(this, { tastyFavoriteEntity ->
            for (savedFavorite in tastyFavoriteEntity) {
                if (savedFavorite.business.id == args.business.id) {
                    changeMenuItemColor(item, R.color.orange)
                    savedId = savedFavorite.id
                    wasSaved = true
                }
            }
        })
    }

    private fun changeMenuItemColor(item: MenuItem?, color: Int) {
        item?.icon?.setTint(ContextCompat.getColor(this, color))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.save_restaurant_to_favorites -> handleFavorites(item)
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleFavorites(item: MenuItem) {
        if (!wasSaved) {
            insertToFavorites(item)
        } else {
            deleteFromFavorites(item)
        }
    }

    private fun insertToFavorites(item: MenuItem) {
        mainViewModel.insertFavorite(args.business)
        changeMenuItemColor(item, R.color.orange)
        showSnackbar("Restaurant saved to Favorites.")
        wasSaved = true
    }

    private fun deleteFromFavorites(item: MenuItem) {
        mainViewModel.deleteFavorite(savedId, args.business)
        changeMenuItemColor(item, R.color.white)
        showSnackbar("Restaurant removed from Favorites.")
        wasSaved = false
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(menuItem, R.color.white)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            binding.businessDetailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
                .show()

    }
}