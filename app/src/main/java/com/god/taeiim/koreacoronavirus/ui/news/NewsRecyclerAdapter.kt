package com.god.taeiim.koreacoronavirus.ui.news

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.god.taeiim.koreacoronavirus.base.BaseRecyclerAdapter
import com.god.taeiim.koreacoronavirus.base.BaseRecyclerViewHolder

class NewsRecyclerAdapter<ITEM : Any, B : ViewDataBinding>(
    layout: Int,
    bindingVariableId: Int
) : BaseRecyclerAdapter<ITEM, B>(layout, bindingVariableId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<B> {

        return BaseRecyclerViewHolder(
            layoutResId,
            parent = parent,
            bindingVariableId = bindingVariableId
        )
    }

}
