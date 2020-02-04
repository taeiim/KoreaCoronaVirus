package com.god.taeiim.koreacoronavirus.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.god.taeiim.koreacoronavirus.R
import com.god.taeiim.koreacoronavirus.databinding.ActivityMainBinding
import com.god.taeiim.koreacoronavirus.ui.diseasewebview.DiseaseControlWebViewFragment
import com.god.taeiim.koreacoronavirus.ui.routemap.RouteMapFragment
import com.god.taeiim.koreacoronavirus.ui.news.NewsFragment
import com.god.taeiim.koreacoronavirus.ui.statistics.CoronaStatisticsFragment
import com.god.taeiim.myapplication.base.BaseActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd
import com.google.android.material.bottomnavigation.BottomNavigationView

const val SHOW_AD_TAB_CLICK_MAX_CNT = 5

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val vm by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        })[MainViewModel::class.java]
    }

    private lateinit var mPublisherInterstitialAd: PublisherInterstitialAd
    private var tabClickCnt = 0
    private var lastTimeBackPressed: Long = -1500

    val statisticsFragment = CoronaStatisticsFragment()
    val routeMapFragment = RouteMapFragment()
    val newsFragment = NewsFragment()
    val webViewFragment = DiseaseControlWebViewFragment()
    var activeFragment: Fragment = statisticsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)
        setUpPublisherAd()

        with(binding) {
            initTabFragment()
            bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            loadFragment(vm.tabSelectedItem.value ?: bottomNavigation.selectedItemId)
        }

    }

    private fun setUpPublisherAd() {
        mPublisherInterstitialAd = PublisherInterstitialAd(this)
        mPublisherInterstitialAd.adUnitId = "/6499/example/interstitial"
        mPublisherInterstitialAd.loadAd(PublisherAdRequest.Builder().build())
        mPublisherInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                mPublisherInterstitialAd.loadAd(PublisherAdRequest.Builder().build())
            }
        }
    }


    private fun initTabFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, statisticsFragment, "1")
            .hide(statisticsFragment).commit()
        supportFragmentManager
            .beginTransaction().add(R.id.fragmentContainer, routeMapFragment, "2")
            .hide(routeMapFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, newsFragment, "3")
            .hide(newsFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, webViewFragment, "4")
            .hide(webViewFragment).commit()
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
            R.id.statisticsNavigation -> statisticsFragment
            R.id.routeMapNavigation -> routeMapFragment
            R.id.safetyInfoNavigation -> newsFragment
            R.id.webViewNavigation -> webViewFragment
            else -> null
        }?.let { showFragment(it) }

        tabClickCnt++
        if (tabClickCnt == SHOW_AD_TAB_CLICK_MAX_CNT) {
            mPublisherInterstitialAd.show()
            tabClickCnt = 0
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().hide(activeFragment).show(fragment).commit()
        activeFragment = fragment

    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed <= 1500)
            finish()
        lastTimeBackPressed = System.currentTimeMillis()
        Toast.makeText(this, "이전 버튼을 한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show()

    }

}