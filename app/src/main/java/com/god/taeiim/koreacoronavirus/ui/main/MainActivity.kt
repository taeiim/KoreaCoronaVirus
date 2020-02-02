package com.god.taeiim.koreacoronavirus.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.god.taeiim.koreacoronavirus.R
import com.god.taeiim.koreacoronavirus.databinding.ActivityMainBinding
import com.god.taeiim.koreacoronavirus.ui.diseasewebview.DiseaseControlWebViewFragment
import com.god.taeiim.koreacoronavirus.ui.routemap.RouteMapFragment
import com.god.taeiim.koreacoronavirus.ui.safetyinfo.SafetyInfoFragment
import com.god.taeiim.koreacoronavirus.ui.statistics.CoronaStatisticsFragment
import com.god.taeiim.myapplication.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val vm by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        })[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            loadFragment(vm.tabSelectedItem.value ?: bottomNavigation.selectedItemId)
        }
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            loadFragment(item.itemId)
            vm.tabSelectedItem.value = item.itemId
            true
        }

    private fun loadFragment(itemId: Int) {
        supportFragmentManager.findFragmentByTag(
            itemId.toString()
        ) ?: when (itemId) {
            R.id.statisticsNavigation -> CoronaStatisticsFragment()
            R.id.routeMapNavigation -> RouteMapFragment()
            R.id.safetyInfoNavigation -> SafetyInfoFragment()
            R.id.webViewNavigation -> DiseaseControlWebViewFragment()
            else -> null
        }?.let { replaceFragment(it) }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment, fragment.tag)
            .commit()
    }
}