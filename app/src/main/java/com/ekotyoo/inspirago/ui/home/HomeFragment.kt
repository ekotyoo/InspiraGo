@file:Suppress("DEPRECATION")

package com.ekotyoo.inspirago.ui.home

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ekotyoo.inspirago.R
import com.ekotyoo.inspirago.databinding.FragmentHomeBinding
import com.ekotyoo.inspirago.databinding.PopupWindowBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRefresh.setOnClickListener {
            viewModel.refreshQuote()
        }

        setupViewModelObserver()
        setupPopupMenu()
        setupShareIntent()
    }

    /**
     * Observe HomeViewModel property needed for this screen
     */
    private fun setupViewModelObserver() {
        viewModel.currentQuote.observe(viewLifecycleOwner) { quote ->
            Glide.with(requireContext())
                .load(quote.bgImageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(binding.ivQuote.drawable)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.ivQuote)

            binding.apply {
                tvQuote.text = quote.content
                tvAuthor.text = quote.author
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.apply {
                progressBar.isGone = !isLoading
                groupedCardContent.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error -> showToast(error) }

        viewModel.allQuote.observe(viewLifecycleOwner) { quotes ->
            val currentQuote = viewModel.currentQuote.value

            // Check if current displayed quote exist in room database or not
            val isQuoteExist = quotes.any {
                currentQuote?.author == it.author && currentQuote.content == it.content && it.isBookmarked
            }

            binding.btnFav.apply {
                // Set image resource and button functionality based on isQuoteExist
                setImageResource(if (isQuoteExist) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
                setOnClickListener {
                    if (!isQuoteExist) {
                        viewModel.saveQuote()
                        showToast("Saved to favorite!")
                    } else {
                        viewModel.deleteQuote(currentQuote!!)
                        showToast("Deleted from favorite!")
                    }
                }
            }
        }
    }

    /**
     * Show toast on screen
     *
     * @param message to show on toast
     */
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Convert quote CardView to Bitmap, and share the result
     * via implicit intent
     */
    private fun setupShareIntent() {
        binding.btnShare.setOnClickListener {
            val cardView = binding.cvQuote

            val bitmap =
                Bitmap.createBitmap(cardView.width, cardView.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            cardView.draw(canvas)

            val path = MediaStore.Images.Media.insertImage(
                activity?.contentResolver,
                bitmap,
                "${System.currentTimeMillis()}",
                "Quote to share"
            )

            val uri: Uri = Uri.parse(path)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
            }
            startActivity(Intent.createChooser(intent, "Share Image"))
        }
    }

    /**
     * Setup popup menu dialog to navigate to SavedFragment and SettingFragment
     */
    private fun setupPopupMenu() {
        val popupBinding = PopupWindowBinding.inflate(layoutInflater)
        val popupWindow = PopupWindow(
            popupBinding.root,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            isFocusable = true
            elevation = 10F
        }

        popupBinding.tvMenuFavorite.setOnClickListener {
            popupWindow.dismiss()
            findNavController().navigate(R.id.action_homeFragment_to_savedFragment)
        }

        popupBinding.tvMenuSetting.setOnClickListener {
            showToast("You clicked setting!")
            popupWindow.dismiss()
        }

        binding.btnMenu.setOnClickListener { btn ->
            popupWindow.showAsDropDown(btn, 0, -btn.height)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}