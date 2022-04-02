package com.ekotyoo.inspirago.ui.home

import android.app.ActionBar
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.view.marginEnd
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ekotyoo.inspirago.R
import com.ekotyoo.inspirago.databinding.FragmentHomeBinding
import com.ekotyoo.inspirago.databinding.PopupWindowBinding

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

        setupPopupMenu()
    }

    private fun setupPopupMenu() {
        val popupBinding = PopupWindowBinding.inflate(layoutInflater)
        val popupWindow = PopupWindow(popupBinding.root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            isFocusable = true
            elevation = 10F
        }

        popupBinding.tvMenuFavorite.setOnClickListener {
            Toast.makeText(requireContext(), "You clicked favorite", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
        }

        popupBinding.tvMenuSetting.setOnClickListener {
            Toast.makeText(requireContext(), "You clicked setting", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
        }

        binding.btnMenu.setOnClickListener { btn ->
            popupWindow.showAsDropDown(btn,  0, -btn.height)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}