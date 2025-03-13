package com.example.onnote.view.fragments.onBoard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onnote.R
import com.example.onnote.databinding.FragmentOnBoardPagerBinding


class OnBoardPagerFragment : Fragment() {

    private lateinit var binding :FragmentOnBoardPagerBinding

    companion object{
        const val ARG_ONBOARD_POSITION= "onBoard"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentOnBoardPagerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        when(requireArguments().getInt(ARG_ONBOARD_POSITION)){
            0 -> {
                binding.txtTitle.text = "Удобство"
                binding.txtBody.text = "Создавайте заметки в два клика! Записывайте мысли, идеи и важные задачи мгновенно."
                binding.lottieAnimation.setAnimation(R.raw.ani_jiraff)
            }
            1 -> {
                binding.txtTitle.text = "Организация"
                binding.txtBody.text ="Организуйте заметки по папкам и тегам. Легко находите нужную информацию в любое время."
                binding.lottieAnimation.setAnimation(R.raw.anim_2)
            }
            2 -> {
                binding.txtTitle.text = "Синхронизация"
                binding.txtBody.text = "Синхронизация на всех устройствах. Доступ к записям в любое время и в любом месте."
                binding.lottieAnimation.setAnimation(R.raw.anim_turt)
            }
        }
    }

}