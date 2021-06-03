package com.softpian.tasty.ui.webpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.softpian.tasty.databinding.FragmentWebPageBinding
import com.softpian.tasty.models.yelp.Business
import com.softpian.tasty.utils.Constants.Companion.BUSINESS_BUNDLE_KEY

class WebPageFragment : Fragment() {

    private var _binding: FragmentWebPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebPageBinding.inflate(inflater, container, false)

        val args = arguments
        val business: Business? = args?.getParcelable(BUSINESS_BUNDLE_KEY)

        binding.webView.webViewClient = object : WebViewClient() {}
        business?.let {
            binding.webView.loadUrl(it.url)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}