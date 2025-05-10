package com.example.onnote.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.onnote.ui.fragments.onBoard.OnBoardPagerFragment

class OnBoardAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =3

    override fun createFragment(position: Int)= OnBoardPagerFragment().apply {
        arguments= Bundle().apply {
            putInt(OnBoardPagerFragment.ARG_ONBOARD_POSITION,position)
        }
    }
}