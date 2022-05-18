package com.sr.myapplication.module.home.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sr.myapplication.R
import com.sr.myapplication.module.home.adapter.CardListAdapter
import com.sr.myapplication.databinding.FragmentListCardBinding
import com.sr.myapplication.module.home.model.DataRepoModel
import com.sr.myapplication.module.home.viewmodel.CardsListViewModel
import kotlinx.coroutines.*

class CardListFragment : Fragment() {
    private lateinit var binding: FragmentListCardBinding
    private var adapter: CardListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_card, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBinding()
        setAdapter()
    }

    private fun setAdapter() {
        adapter = CardListAdapter()
        binding.itemList.adapter = adapter
        binding.itemList.layoutManager = LinearLayoutManager(activity)
    }


    private fun initBinding() {
        val mViewModel = CardsListViewModel()
        binding.viewModel = mViewModel

        //invoke service
        fetchList(mViewModel)
        //observe data
        observeData(mViewModel)
    }

    private fun fetchList(mViewModel: CardsListViewModel) {
        // fetch list to be displayed
        mViewModel.fetchList()
    }

    private fun observeData(mViewModel: CardsListViewModel) {
        // Update the list when the data changes
        mViewModel.getListObservable()
            .observe(viewLifecycleOwner, { data: DataRepoModel? ->
                if (data?.dataModel != null) {

                    // set list to recycler view adapter
                    adapter?.setList(data.dataModel)

                    // dismiss progress
                    mViewModel.isLoading.set(false)
                    mViewModel.isError.set(false)
                } else {
                    mViewModel.isLoading.set(false)
                    mViewModel.isError.set(true)
                    if (data?.throwable != null && !TextUtils.isEmpty(data.throwable!!.message)) {
                        Toast.makeText(
                            activity,
                            data.throwable?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    companion object {
        fun newInstance(): CardListFragment {
            return CardListFragment()
        }
    }
}