package com.sr.myapplication.module.home.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sr.myapplication.R
import com.sr.myapplication.core.app.ApiConstants.BUNDLE_KEY_DATA_MODEL
import com.sr.myapplication.core.base.SchedulerProvider
import com.sr.myapplication.databinding.FragmentListCardBinding
import com.sr.myapplication.module.detail.ui.CardsDetailActivity
import com.sr.myapplication.module.home.adapter.CardListAdapter
import com.sr.myapplication.module.home.model.DataRepoModel
import com.sr.myapplication.module.home.viewmodel.CardsListViewModel

class CardListFragment : Fragment() {
    private lateinit var binding: FragmentListCardBinding
    private var adapter: CardListAdapter? = null
    private lateinit var mViewModel: CardsListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        adapter?.listener = {
            val intent = Intent(this.activity, CardsDetailActivity::class.java)
            intent.putExtra(
                BUNDLE_KEY_DATA_MODEL,
                mViewModel.getListLiveData().value?.dataModel?.get(it)
            )
            this.activity?.startActivity(intent)
        }
        binding.itemList.adapter = adapter
        binding.itemList.layoutManager = LinearLayoutManager(activity)
    }


    private fun initBinding() {
        // init view Model
        mViewModel = ViewModelProvider(this)[CardsListViewModel::class.java]
        mViewModel.init(SchedulerProvider())
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
        mViewModel.getListLiveData()
            .observe(viewLifecycleOwner, { data: DataRepoModel? ->
                if (data?.dataModel != null && data.throwable == null) {

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