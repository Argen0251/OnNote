package com.example.onnote.ui.fragments.onBoard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.onnote.R
import com.example.onnote.databinding.FragmentOnBoardBinding
import com.example.onnote.databinding.FragmentOnBoardPagerBinding
import com.example.onnote.ui.adapters.OnBoardAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOnBoardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListener()
        setupTabLayout()
    }

    private fun setupListener() {

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    binding.txtSkip.visibility = View.INVISIBLE
                }
                else{
                    binding.txtSkip.visibility = View.VISIBLE
                    binding.txtSkip.setOnClickListener {
                        binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 2, true)
                    }
                }
            }
        })
    }

    private fun initialize() {
        binding.viewPager.adapter =OnBoardAdapter(this)
    }
    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, _ ->
            tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.dots)
        }.attach()
    }



}