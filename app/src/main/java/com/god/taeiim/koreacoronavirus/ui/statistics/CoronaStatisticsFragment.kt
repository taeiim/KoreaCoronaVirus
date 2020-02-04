package com.god.taeiim.koreacoronavirus.ui.statistics

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.god.taeiim.koreacoronavirus.R
import com.god.taeiim.koreacoronavirus.data.FirebaseRepositoryImpl
import com.god.taeiim.koreacoronavirus.data.remote.FirebaseRemoteDataSourceImpl
import com.god.taeiim.koreacoronavirus.databinding.FragmentCoronaStatisticsBinding
import com.god.taeiim.myapplication.base.BaseFragment
import com.god.taeiim.myapplication.extensions.linkIntent

class CoronaStatisticsFragment :
    BaseFragment<FragmentCoronaStatisticsBinding>(R.layout.fragment_corona_statistics) {

    private val vm: CoronaStatisticsViewModel by lazy {
        ViewModelProvider(this@CoronaStatisticsFragment, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CoronaStatisticsViewModel(
                    FirebaseRepositoryImpl.getInstance(FirebaseRemoteDataSourceImpl)
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

        binding.showInfoDialogBtn.setOnClickListener { showDeveloperInfoDialog() }
    }

    private fun showDeveloperInfoDialog() {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_developer_info)

        val infoLayout = dialog.findViewById(R.id.infoDeveloperLayout) as LinearLayout
        val closeBtn = dialog.findViewById(R.id.closeBtn) as ImageView
        val facebookLink = "https://www.facebook.com/profile.php?id=100004908165474"
        infoLayout.setOnClickListener { facebookLink.linkIntent(context!!) }
        closeBtn.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }


    private fun CoronaStatisticsViewModel.setObserves() {
        errorFailGetData.observe(viewLifecycleOwner, Observer { failToGetData() })
    }

    private fun failToGetData() {
        Toast.makeText(context, getString(R.string.fail_get_firebase), Toast.LENGTH_SHORT).show()
    }

}