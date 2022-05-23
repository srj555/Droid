package com.sr.myapplication.module.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sr.myapplication.databinding.FragmentDetailBinding
import com.sr.myapplication.module.home.model.DataModel

class CardDetailFragment(val dataModel:DataModel?) : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }


    private fun init() {
        binding.detailTitleTV.text = dataModel?.name
        activity?.baseContext?.let {
            Glide.with(it)
                .load(dataModel?.links?.patch?.small)
                .into(binding.detailIV)
        }
    }


    companion object {
        fun newInstance(dataModel: DataModel?): CardDetailFragment {
            return CardDetailFragment(dataModel)
        }
    }
}