package com.ekotyoo.inspirago.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ekotyoo.inspirago.R
import com.ekotyoo.inspirago.databinding.FragmentHomeBinding

private const val dummyImageUrl = "https://images.unsplash.com/photo-1648780693516-3ff39ff65d1e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=735&q=80"

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(binding.root)
            .load(dummyImageUrl)
            .into(binding.ivQuote)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}