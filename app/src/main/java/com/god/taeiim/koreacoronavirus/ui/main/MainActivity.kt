package com.god.taeiim.koreacoronavirus.ui.main

import android.os.Bundle
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
import com.google.android.gms.ads.AdRequest
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)
        bannerAdmob()
        setUpPublisherAd()

        with(binding) {
            bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            loadFragment(vm.tabSelectedItem.value ?: bottomNavigation.selectedItemId)
        }

    }

    private fun bannerAdmob() {
        binding.adView.loadAd(AdRequest.Builder().build())

        binding.adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
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
            R.id.safetyInfoNavigation -> NewsFragment()
            R.id.webViewNavigation -> DiseaseControlWebViewFragment()
            else -> null
        }?.let { replaceFragment(it) }

        tabClickCnt++
        if (tabClickCnt == SHOW_AD_TAB_CLICK_MAX_CNT) {
            mPublisherInterstitialAd.show()
            tabClickCnt = 0
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment, fragment.tag)
            .commit()
    }
}