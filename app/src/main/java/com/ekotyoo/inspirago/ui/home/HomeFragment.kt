@file:Suppress("DEPRECATION")

package com.ekotyoo.inspirago.ui.home

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
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


private const val dummyImageUrl =
    "https://picsum.photos/300/500"

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory.getInstance()
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

        binding.floatingActionButton.setOnClickListener {
            viewModel.getRandomQuote()
        }

        viewModel.currentQuote.observe(viewLifecycleOwner) { quote ->
            Glide.with(requireContext())
                .load(dummyImageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(binding.ivQuote.drawable)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.ivQuote)
            binding.apply {
                tvQuote.text = quote.content
                tvAuthor.text = quote.author
            }
            val motionLayout = binding.root
            setTransition(motionLayout, R.id.start, R.id.end)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        setupPopupMenu()
        setupShareIntent()
    }

    private fun setTransition(motionLayout: MotionLayout, startState: Int, endState: Int) {
        motionLayout.setTransition(startState, endState)
        motionLayout.setTransitionDuration(1000)
        motionLayout.transitionToEnd {
            viewModel.isFirstLaunch.observe(viewLifecycleOwner) { isFirstLaunch ->
                if (isFirstLaunch) {
                    motionLayout.transitionToStart()
                }
            }
        }
        viewModel.setIsFirstLaunch(false)
    }

    private fun setupShareIntent() {
        binding.btnShare.setOnClickListener {
            val cardView = binding.cvQuote

            val bitmap = Bitmap.createBitmap(cardView.width, cardView.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            cardView.draw(canvas)
            canvas.save()
            canvas.translate(40F, 60F)
            binding.ivQuoteDecoration.draw(canvas)
            canvas.translate(60F, 120F)
            binding.tvQuote.draw(canvas)
            canvas.translate(600F, 700F)
            binding.tvAuthor.draw(canvas)
            canvas.restore()

            val path = MediaStore.Images.Media.insertImage(
                activity?.contentResolver,
                bitmap,
                "Tittle",
                "Desc"
            )
            val uri: Uri = Uri.parse(path)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
            }
            startActivity(Intent.createChooser(intent, "Share Image"))
        }
    }

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
            Toast.makeText(requireContext(), "You clicked setting", Toast.LENGTH_SHORT).show()
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