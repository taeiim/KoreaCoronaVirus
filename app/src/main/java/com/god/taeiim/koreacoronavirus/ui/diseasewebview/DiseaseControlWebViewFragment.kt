package com.god.taeiim.koreacoronavirus.ui.diseasewebview

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.god.taeiim.koreacoronavirus.R
import com.god.taeiim.koreacoronavirus.data.FirebaseRepositoryImpl
import com.god.taeiim.koreacoronavirus.data.remote.FirebaseRemoteDataSourceImpl
import com.god.taeiim.koreacoronavirus.databinding.FragmentDiseaseWebviewBinding
import com.god.taeiim.myapplication.base.BaseFragment

class DiseaseControlWebViewFragment :
    BaseFragment<FragmentDiseaseWebviewBinding>(R.layout.fragment_disease_webview) {

    private val vm: DiseaseControlWebViewViewModel by lazy {
        ViewModelProvider(this@DiseaseControlWebViewFragment, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DiseaseControlWebViewViewModel(
                    FirebaseRepositoryImpl.getInstance(FirebaseRemoteDataSourceImpl.getInstance())
                ) as T
            }
        })[DiseaseControlWebViewViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm
        binding.lifecycleOwner = this

        vm.setObserves()
        vm.getWebViewURL()

    }

    private fun DiseaseControlWebViewViewModel.setObserves() {
        webViewURL.observe(viewLifecycleOwner, Observer { loadWebView() })
    }

    private fun loadWebView() {
        with(binding) {
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webView.loadUrl(vm?.webViewURL?.value)
        }
    }

}