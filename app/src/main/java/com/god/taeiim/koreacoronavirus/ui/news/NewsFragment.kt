package com.god.taeiim.koreacoronavirus.ui.news

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.god.taeiim.koreacoronavirus.BR
import com.god.taeiim.koreacoronavirus.R
import com.god.taeiim.koreacoronavirus.api.model.SearchResultNews
import com.god.taeiim.koreacoronavirus.data.FirebaseRepositoryImpl
import com.god.taeiim.koreacoronavirus.data.remote.FirebaseRemoteDataSourceImpl
import com.god.taeiim.koreacoronavirus.data.remote.NaverRemoteDataSourceImpl
import com.god.taeiim.koreacoronavirus.databinding.FragmentNewsBinding
import com.god.taeiim.koreacoronavirus.databinding.ItemKeywordBinding
import com.god.taeiim.koreacoronavirus.databinding.ItemNewsBinding
import com.god.taeiim.koreacoronavirus.base.BaseFragment
import com.god.taeiim.koreacoronavirus.data.NaverRepositoryImpl

class NewsFragment : BaseFragment<FragmentNewsBinding>(R.layout.fragment_news) {

    private val vm: NewsViewModel by lazy {
        ViewModelProvider(this@NewsFragment, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NewsViewModel(
                    FirebaseRepositoryImpl.getInstance(FirebaseRemoteDataSourceImpl),
                    NaverRepositoryImpl.getInstance(NaverRemoteDataSourceImpl)
                ) as T
            }
        })[NewsViewModel::class.java]
    }

    lateinit var searchKeywordsAdapter: KeywordsRecyclerAdapter<SearchKeyword, ItemKeywordBinding>
    lateinit var newsResultAdapter: NewsRecyclerAdapter<SearchResultNews.Item, ItemNewsBinding>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this

        searchKeywordsAdapter = KeywordsRecyclerAdapter(R.layout.item_keyword, BR.item, vm)
        newsResultAdapter = NewsRecyclerAdapter(R.layout.item_news, BR.item)
        binding.keywordRecyclerView.adapter = searchKeywordsAdapter
        binding.newsRecyclerView.adapter = newsResultAdapter

        vm.setObserves()
        vm.getHotSearchKeyword()

    }

    private fun NewsViewModel.setObserves() {
        hotSearchKeywords.observe(viewLifecycleOwner, Observer { updateSearchKeywords(it) })

        errorFailGetKeywords.observe(viewLifecycleOwner, Observer { updateBasicSearchKeywords() })

        currentSearchKeyword.observe(viewLifecycleOwner, Observer { vm.searchNews() })

        searchNewsResultList.observe(viewLifecycleOwner, Observer { updateNewsResults(it) })

        errorFailSearchNews.observe(viewLifecycleOwner, Observer { failToGetNews() })
    }

    private fun updateSearchKeywords(keywords: List<SearchKeyword>) {
        searchKeywordsAdapter.updateItems(keywords)
    }

    private fun updateBasicSearchKeywords() {
        val basicKeywords = arrayOf("코로나 바이러스", "우한 폐렴", "확진자", "코로나 바이러스 증상", "신종 코로나")
        val keywords = basicKeywords.mapIndexed { index, keyword -> SearchKeyword(index, keyword) }
        searchKeywordsAdapter.updateItems(keywords)
    }

    private fun updateNewsResults(news: List<SearchResultNews.Item>) {
        newsResultAdapter.updateItems(news)
        binding.newsRecyclerView.scrollTo(0, 0)
    }

    private fun failToGetNews() {
        newsResultAdapter.clearItems()
        Toast.makeText(context, getString(R.string.fail_get_firebase), Toast.LENGTH_SHORT).show()
    }
}