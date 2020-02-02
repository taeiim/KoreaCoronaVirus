package com.god.taeiim.koreacoronavirus.ui.statistics

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.god.taeiim.koreacoronavirus.R
import com.god.taeiim.koreacoronavirus.data.FirebaseRepositoryImpl
import com.god.taeiim.koreacoronavirus.data.remote.FirebaseRemoteDataSourceImpl
import com.god.taeiim.koreacoronavirus.databinding.FragmentCoronaStatisticsBinding
import com.god.taeiim.myapplication.base.BaseFragment

class CoronaStatisticsFragment :
    BaseFragment<FragmentCoronaStatisticsBinding>(R.layout.fragment_corona_statistics) {

    private val vm: CoronaStatisticsViewModel by lazy {
        ViewModelProvider(this@CoronaStatisticsFragment, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CoronaStatisticsViewModel(
                    FirebaseRepositoryImpl.getInstance(FirebaseRemoteDataSourceImpl.getInstance())
                ) as T
            }
        })[CoronaStatisticsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm
        binding.lifecycleOwner = this

        vm.setObserves()
        vm.getStatisticsData()
    }

    private fun CoronaStatisticsViewModel.setObserves() {
        errorFailGetData.observe(viewLifecycleOwner, Observer { failToGetData() })
    }

    private fun failToGetData() {
        Toast.makeText(context, getString(R.string.fail_get_statistics), Toast.LENGTH_SHORT).show()
    }

}