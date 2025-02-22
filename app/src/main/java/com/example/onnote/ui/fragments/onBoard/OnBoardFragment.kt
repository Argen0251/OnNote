package com.example.onnote.ui.fragments.onBoard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.onnote.R
import com.example.onnote.databinding.FragmentOnBoardBinding
import com.example.onnote.ui.utils.PreferenceHelper
import com.google.android.material.tabs.TabLayoutMediator
import androidx.core.content.ContextCompat
import com.example.onnote.ui.adapters.OnBoardAdapter

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding
    private val shared = PreferenceHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shared.unit(requireContext())
        if (shared.onBoardShown) {
            findNavController().navigate(R.id.action_onBoardFragment_to_noteFragment)
        }
        initialize()
        setupListener()
        setupTabLayout()
    }

    private fun setupListener() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.txtSkip.visibility = if (position == 2) View.INVISIBLE else View.VISIBLE

                binding.btnBegin.visibility = if (position == 2) View.VISIBLE else View.INVISIBLE
                binding.txtSkip.setOnClickListener {
                    binding.viewPager.setCurrentItem(2, true)
                }
            }
        })
        binding.btnBegin.setOnClickListener {
            shared.onBoardShown = true
            findNavController().navigate(R.id.action_onBoardFragment_to_noteFragment)
        }
    }

    private fun initialize() {
        binding.viewPager.adapter = OnBoardAdapter(this)
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, _ ->
            tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.dots)
        }.attach()
    }
}
